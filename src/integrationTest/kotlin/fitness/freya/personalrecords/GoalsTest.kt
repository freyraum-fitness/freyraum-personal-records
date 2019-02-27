package fitness.freya.personalrecords

import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.mapping.GoalMapper
import fitness.freya.personalrecords.repository.GoalRepository
import fitness.freya.personalrecords.utils.FreyRaumWireMockRule
import fitness.freya.personalrecords.utils.KPostgresContainer
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.mapper.ObjectMapperType
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Duration
import java.util.ArrayList
import java.util.UUID
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoalsTest(
    @LocalServerPort
    private var randomServerPort: Int) {

  @Autowired
  lateinit var goalRepository: GoalRepository

  @Autowired
  lateinit var goalMapper: GoalMapper

  companion object {
    private const val expectedGoalsSize = 4

    private val freyraumWireMockRule = FreyRaumWireMockRule()
    private val postgreSQLContainer = KPostgresContainer("postgres:10.4")
        .withDatabaseName("freyafitness")
        .withUsername("testuser")
        .withPassword("testpassword")
        .withStartupTimeout(Duration.ofSeconds(600))

    @BeforeAll
    @JvmStatic
    @Suppress("unused")
    fun before() {
      freyraumWireMockRule.start()
      postgreSQLContainer.start()
    }

    @AfterAll
    @JvmStatic
    @Suppress("unused")
    fun afterAll() {
      freyraumWireMockRule.stop()
      postgreSQLContainer.stop()
    }

  }

  @Nested
  @DisplayName("A USER can")
  inner class UserCan {

    @BeforeEach
    fun `an authenticated USER can`() {
      freyraumWireMockRule.withAuthenticatedUser()
    }

    @Test
    fun `get all goals`() {
      val goals = getAllGoals()

      assertThat(goals).hasSize(expectedGoalsSize)
    }

    @Test
    fun `not create goals`() {
      canNotCreateGoal()
    }

    @Test
    fun `get an goal by id`() {
      val goal = getGoalById(TestData.BACK_SQUAT_MAX_WEIGHT.id!!)

      assertThat(goal).isNotNull
    }

  }

  @Nested
  @DisplayName("A TRAINER")
  inner class TrainerCan {

    @BeforeEach
    fun `a trainer`() {
      freyraumWireMockRule.withAuthenticatedTrainer()
    }

    @Test
    fun `can get all goals`() {
      val goals = getAllGoals()

      assertThat(goals).hasSize(expectedGoalsSize)
    }

    @Test
    fun `can not create goals`() {
      canNotCreateGoal()
    }

    @Test
    fun `get an goal by id`() {
      val goal = getGoalById(TestData.BACK_SQUAT_MAX_WEIGHT.id!!)

      assertThat(goal).isNotNull
    }
  }

  @Nested
  @DisplayName("An ADMIN")
  inner class AdminCan {

    @BeforeEach
    fun `an admin`() {
      freyraumWireMockRule.withAuthenticatedAdmin()
    }

    @Test
    fun `can get all goals`() {
      val goals = getAllGoals()

      assertThat(goals).hasSize(expectedGoalsSize)
    }

    @Test
    fun `can create goals`() {
      val goal = createGoal(goalMapper.map(TestData.BACK_SQUAT_MAX_WEIGHT))
          .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .extract()
          .`as`(GoalDto::class.java)

      assertThat(goal.id).isNotNull()
      goalRepository.deleteById(goal.id!!)
    }

    @Test
    fun `get an goal by id`() {
      val goal = getGoalById(TestData.BACK_SQUAT_MAX_WEIGHT.id!!)

      assertThat(goal).isNotNull
    }
  }

  private fun getAllGoals(): List<GoalDto> = RestAssured.given()
      .baseUri("http://localhost")
      .port(randomServerPort)
      .header(HttpHeaders.AUTHORIZATION, "Bearer xyz123")
      .get("/goals")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .extract()
      .`as`(ArrayList<GoalDto>()::class.java)

  private fun getGoalById(id: UUID): GoalDto = RestAssured.given()
      .baseUri("http://localhost")
      .port(randomServerPort)
      .header(HttpHeaders.AUTHORIZATION, "Bearer xyz123")
      .get("/goals/$id")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .extract()
      .`as`(GoalDto::class.java)

  private fun canNotCreateGoal() {
    createGoal(goalMapper.map(TestData.BACK_SQUAT_MAX_WEIGHT))
        .then()
        .statusCode(400)
  }

  private fun createGoal(goal: GoalDto): Response = RestAssured.given()
      .baseUri("http://localhost")
      .port(randomServerPort)
      .header(HttpHeaders.AUTHORIZATION, "Bearer xyz123")
      .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
      .body(goal.copy(id = null), ObjectMapperType.JACKSON_2)
      .post("/goals")

  @TestConfiguration
  class TestConfig {
    @Bean
    fun dataSource(): DataSource {
      val dataSource = DriverManagerDataSource()
      dataSource.setDriverClassName("org.postgresql.Driver")
      dataSource.url = postgreSQLContainer.jdbcUrl
      dataSource.username = postgreSQLContainer.username
      dataSource.password = postgreSQLContainer.password
      return dataSource
    }
  }

}


