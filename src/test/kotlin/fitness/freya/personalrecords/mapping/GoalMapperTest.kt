package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.api.dto.ValidityDto
import fitness.freya.personalrecords.model.Unit
import fitness.freya.personalrecords.model.Winning
import fitness.freya.personalrecords.repository.ExerciseRepository
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class GoalMapperTest {

  @InjectMocks
  lateinit var testee: GoalMapper

  @Suppress("unused")
  @Spy
  lateinit var validityMapper: ValidityMapper

  @Mock
  lateinit var exerciseRepository: ExerciseRepository

  val backSquatMaxWeightDto = GoalDto(
      id = TestData.BACK_SQUAT_MAX_WEIGHT.id,
      exerciseId = TestData.BACK_SQUAT.id!!,
      unit = Unit.REPETITIONS,
      comparingUnit = Unit.KILOGRAMS,
      winning = Winning.MORE,
      description = "squat as much as you can for a given number of reps",
      validity = ValidityDto(
          TestData.VALID.from,
          TestData.VALID.to)
  )

  @Test
  fun `map Goal to GoalDto`() {
    val goal = TestData.BACK_SQUAT_MAX_WEIGHT

    val dto = testee.map(goal)

    assertThat(dto).isEqualTo(backSquatMaxWeightDto)
  }

  @Test
  fun `map GoalDto to Goal`() {
    `when`(exerciseRepository.findById(TestData.BACK_SQUAT.id!!))
        .thenReturn(Optional.of(TestData.BACK_SQUAT))

    val goal = testee.map(backSquatMaxWeightDto)

    assertThat(goal).isEqualTo(TestData.BACK_SQUAT_MAX_WEIGHT)
  }

  @Test
  fun `apply changes to Goal`() {
    `when`(exerciseRepository.findById(TestData.ROW.id!!))
        .thenReturn(Optional.of(TestData.ROW))
    val changes = testee.map(TestData.BACK_SQUAT_MAX_WEIGHT).copy(
        exerciseId = TestData.ROW.id!!,
        description = "Back squat is now rowing",
        unit = Unit.METERS,
        comparingUnit = Unit.SECONDS,
        winning = Winning.LESS,
        validity = validityMapper.map(TestData.NOT_YET_VALID)
    )

    val result = testee.apply(TestData.BACK_SQUAT_MAX_WEIGHT, changes)

    assertThat(result.exercise).isEqualTo(TestData.ROW)
    assertThat(result.description).isEqualTo("Back squat is now rowing")
    assertThat(result.unit).isEqualTo(Unit.METERS)
    assertThat(result.comparingUnit).isEqualTo(Unit.SECONDS)
    assertThat(result.winning).isEqualTo(Winning.LESS)
    assertThat(result.validity).isEqualTo(TestData.NOT_YET_VALID)
  }

}
