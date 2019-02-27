package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.model.Validity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@DataJpaTest
class ExerciseRepositoryTest {

  @Autowired
  lateinit var entityManager: TestEntityManager

  @Autowired
  lateinit var testee: ExerciseRepository

  @Test
  fun `save exercise`() {
    val result = testee.save(TestData.BENCH_PRESS.copy(id = null))

    assertThat(result).isNotNull
    assertThat(result.id).isNotNull()
  }

  @Test
  fun `find exercise by id`() {
    val given = testee.save(TestData.BENCH_PRESS.copy(id = null))

    val result = testee.findById(given.id!!)

    assertThat(result).isPresent
    assertThat(result.get().id).isEqualTo(given.id)
  }

  @Test
  fun `delete exercise by id`() {
    val given = testee.save(TestData.BENCH_PRESS.copy(id = null))

    testee.deleteById(given.id!!)

    assertThat(testee.findById(given.id!!)).isNotPresent
  }

  @Test
  fun `find all valid exercises`() {
    val now = LocalDateTime.now()

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(21)
  }

  @Test
  fun `exclude not yet valid exercises`() {
    val now = LocalDateTime.now()
    givenExercise(TestData.BACK_SQUAT, TestData.NOT_YET_VALID)
    givenExercise(TestData.ROW, TestData.VALID)
    givenExercise(TestData.ROW, TestData.NOT_YET_VALID)

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(22) // there are 21 in testdata
  }

  @Test
  fun `exclude expired exercises`() {
    val now = LocalDateTime.now()
    givenExercise(TestData.BACK_SQUAT, TestData.NOT_ANYMORE_VALID)
    givenExercise(TestData.ROW, TestData.VALID)
    givenExercise(TestData.ROW, TestData.VALID)

    val result = testee.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)

    assertThat(result).isNotNull
    assertThat(result).hasSize(23) // there are 21 in testdata
  }

  fun givenExercise(exercise: Exercise, validity: Validity) {
    entityManager.persist(exercise.copy(id = null, validity = validity))
    entityManager.flush()
    entityManager.clear()
  }

}