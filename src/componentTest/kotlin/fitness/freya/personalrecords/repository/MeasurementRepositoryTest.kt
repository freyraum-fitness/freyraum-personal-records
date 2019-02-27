package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.model.Measurement
import fitness.freya.personalrecords.model.Validity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.UUID

@ExtendWith(SpringExtension::class)
@DataJpaTest
class MeasurementRepositoryTest {

  @Autowired
  lateinit var testee: MeasurementRepository

  @Autowired
  lateinit var goalRepository: GoalRepository

  @Autowired
  lateinit var exerciseRepository: ExerciseRepository

  lateinit var userId: UUID
  lateinit var backSquat: Exercise
  lateinit var backSquatMaxWeight: Goal

  @BeforeEach
  fun `set up exercises`() {
    userId = UUID.randomUUID()
    backSquat = givenExercise(TestData.BACK_SQUAT, TestData.VALID)
    backSquatMaxWeight = givenGoal(TestData.BACK_SQUAT_MAX_WEIGHT.copy(exercise = backSquat), TestData.VALID)
  }

  @Test
  fun `save measurement`() {
    val measurement = Measurement(
        id = null,
        userId = userId,
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 80,
        date = LocalDate.now())

    val result = testee.save(measurement)

    assertThat(result).isNotNull
    assertThat(result.id).isNotNull()
    assertThat(result.userId).isEqualTo(userId)
  }

  @Test
  fun `find measurement by id`() {
    val measurement = Measurement(
        id = null,
        userId = userId,
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 80,
        date = LocalDate.now())
    val given = testee.save(measurement)

    val result = testee.findById(given.id!!)

    assertThat(result).isPresent
    assertThat(result.get().id).isEqualTo(given.id)
  }

  @Test
  fun `delete measurement by id`() {
    val measurement = Measurement(
        id = null,
        userId = userId,
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 80,
        date = LocalDate.now())
    val given = testee.save(measurement)

    testee.deleteById(given.id!!)

    assertThat(testee.findById(given.id!!)).isNotPresent
  }

  @Test
  fun `find by user id`() {
    testee.save(Measurement(
        id = null,
        userId = userId,
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 80,
        date = LocalDate.now()))
    testee.save(Measurement(
        id = null,
        userId = UUID.randomUUID(),
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 80,
        date = LocalDate.now()))
    testee.save(Measurement(
        id = null,
        userId = userId,
        goal = backSquatMaxWeight,
        value = 1,
        comparingValue = 90,
        date = LocalDate.now()))

    val result = testee.findByUserId(userId)

    assertThat(result).hasSize(2)
  }

  fun givenGoal(goal: Goal, validity: Validity): Goal {
    return goalRepository.save(goal.copy(id = null, validity = validity))
  }

  fun givenExercise(exercise: Exercise, validity: Validity): Exercise {
    return exerciseRepository.save(exercise.copy(id = null, validity = validity))
  }

}