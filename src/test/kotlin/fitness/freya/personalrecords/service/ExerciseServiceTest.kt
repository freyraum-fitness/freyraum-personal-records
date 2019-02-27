package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.api.dto.ExerciseDto
import fitness.freya.personalrecords.api.dto.ValidityDto
import fitness.freya.personalrecords.mapping.ExerciseMapper
import fitness.freya.personalrecords.mapping.ValidityMapper
import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.repository.ExerciseRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ExerciseServiceTest(
    @Mock var exerciseRepository: ExerciseRepository) {

  private fun <T> any(): T = Mockito.any<T>()

  private lateinit var testee: ExerciseService
  private lateinit var exerciseMapper: ExerciseMapper

  @BeforeEach
  fun `set up`() {
    exerciseMapper = ExerciseMapper(validityMapper = ValidityMapper())
    testee = ExerciseService(exerciseRepository, exerciseMapper)

    `when`(exerciseRepository.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(any(), any()))
        .thenReturn(listOf(TestData.BACK_SQUAT, TestData.ROW, TestData.BENCH_PRESS))
    `when`(exerciseRepository.findById(TestData.BACK_SQUAT.id!!))
        .thenReturn(Optional.of(TestData.BACK_SQUAT))
    `when`(exerciseRepository.save(Mockito.any(Exercise::class.java)))
        .thenAnswer { it.arguments[0] }
  }

  @Test
  fun `get all current exercises`() {
    val current = testee.getCurrentExercises()

    assertThat(current.size, `is`(3))
  }

  @Test
  fun `get back squat exercise by ID`() {
    val backSquat = testee.getExercise(TestData.BACK_SQUAT.id!!)

    assertThat(backSquat.get(), `is`(TestData.BACK_SQUAT))
  }

  @Test
  fun `should return saved entity`() {
    val row = testee.create(TestData.ROW)

    assertThat(row, `is`(TestData.ROW))
  }

  @Test
  fun `should return updated entity`() {
    val updated = testee.update(TestData.BACK_SQUAT.id!!,
        ExerciseDto(
            id = TestData.BACK_SQUAT.id,
            name = "Kniebeugen",
            abbreviation = TestData.BACK_SQUAT.abbreviation,
            description = TestData.BACK_SQUAT.description,
            validity = ValidityDto(TestData.VALID.from, TestData.VALID.to)
        )
    )

    assertThat(updated, `is`(TestData.BACK_SQUAT.copy(name = "Kniebeugen")))
  }


}