package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.model.Validity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@DataJpaTest
class GoalRepositoryTest {

  @Autowired
  lateinit var testee: GoalRepository

  @Autowired
  lateinit var exerciseRepository: ExerciseRepository

  lateinit var backSquat: Exercise
  lateinit var row: Exercise
  lateinit var benchPress: Exercise

  @BeforeEach
  fun `set up exercises`() {
    backSquat = givenExercise(TestData.BACK_SQUAT, TestData.VALID)
    row = givenExercise(TestData.ROW, TestData.VALID)
    benchPress = givenExercise(TestData.BENCH_PRESS, TestData.VALID)
  }

  @Test
  fun `save goal`() {
    val result = testee.save(TestData.BACK_SQUAT_MAX_WEIGHT.copy(id = null))

    assertThat(result).isNotNull
    assertThat(result.id).isNotNull()
  }

  @Test
  fun `find goal by id`() {
    val given = testee.save(TestData.BACK_SQUAT_MAX_WEIGHT.copy(id = null))

    val result = testee.findById(given.id!!)

    assertThat(result).isPresent
    assertThat(result.get().id).isEqualTo(given.id)
  }

  @Test
  fun `delete goal by id`() {
    val given = testee.save(TestData.BACK_SQUAT_MAX_WEIGHT.copy(id = null))

    testee.deleteById(given.id!!)

    assertThat(testee.findById(given.id!!)).isNotPresent
  }

  @Test
  fun `find all valid goals`() {
    val now = LocalDateTime.now()
    givenGoal(TestData.BACK_SQUAT_MAX_WEIGHT, TestData.VALID)
    givenGoal(TestData.ROW_FASTEST, TestData.VALID)
    givenGoal(TestData.BENCH_PRESS_MAX_WEIGHT, TestData.VALID)

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(7)
  }

  @Test
  fun `exclude not yet valid goals`() {
    val now = LocalDateTime.now()
    givenGoal(TestData.BACK_SQUAT_MAX_WEIGHT, TestData.NOT_YET_VALID)
    givenGoal(TestData.ROW_FASTEST, TestData.VALID)
    givenGoal(TestData.BENCH_PRESS_MAX_WEIGHT, TestData.NOT_YET_VALID)

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(5)
  }

  @Test
  fun `exclude expired goals`() {
    val now = LocalDateTime.now()
    givenGoal(TestData.BACK_SQUAT_MAX_WEIGHT, TestData.NOT_ANYMORE_VALID)
    givenGoal(TestData.ROW_FASTEST, TestData.VALID)
    givenGoal(TestData.BENCH_PRESS_MAX_WEIGHT, TestData.VALID)

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(6)
  }

  @Test
  fun `find by exercise id`() {
    val g = givenGoal(TestData.ROW_FASTEST.copy(exercise = row), TestData.VALID)
    givenGoal(TestData.ROW_CALORIES.copy(exercise = row), TestData.VALID)

    val result = testee.findByExercise(row)

    assertThat(result).hasSize(2)
  }

  fun givenGoal(goal: Goal, validity: Validity): Goal {
    return testee.save(goal.copy(id = null, validity = validity))
  }

  fun givenExercise(exercise: Exercise, validity: Validity): Exercise {
    return exerciseRepository.save(exercise.copy(id = null, validity = validity))
  }

}