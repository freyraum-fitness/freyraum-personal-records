package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.api.dto.ExerciseDto
import fitness.freya.personalrecords.api.dto.ValidityDto
import org.assertj.core.api.Java6Assertions
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ExerciseMapperTest {

  @InjectMocks
  lateinit var testee: ExerciseMapper

  @Suppress("unused")
  @Spy
  lateinit var validityMapper: ValidityMapper

  val backSquatDto = ExerciseDto(
      id = TestData.BACK_SQUAT.id,
      name = "Back Squat",
      abbreviation = "BS",
      description = TestData.BACK_SQUAT.description,
      validity = ValidityDto(
          from = TestData.BACK_SQUAT.validity.from,
          to = TestData.BACK_SQUAT.validity.to
      )
  )

  @Test
  fun `should map Exercise to ExerciseDto`() {
    val result = testee.map(TestData.BACK_SQUAT)

    assertThat(result, `is`(backSquatDto))
  }

  @Test
  fun `should map ExerciseDto to Exercise`() {
    val result = testee.map(backSquatDto)

    assertThat(result, `is`(TestData.BACK_SQUAT))
  }


  @Test
  fun `should map ExerciseDto to Exercise and back`() {
    val result = testee.map(backSquatDto)
    val back = testee.map(result)

    assertThat(back, `is`(backSquatDto))
  }

  @Test
  fun `should map Exercise to ExerciseDto and back`() {
    val result = testee.map(TestData.ROW)
    val back = testee.map(result)

    assertThat(back, `is`(TestData.ROW))
  }

  @Test
  fun `apply changes to Goal`() {
    val changes = testee.map(TestData.BACK_SQUAT).copy(
        name = "Row",
        abbreviation = "ROW",
        description = "Back squat is now rowing",
        validity = validityMapper.map(TestData.NOT_YET_VALID)
    )

    val result = testee.apply(TestData.BACK_SQUAT, changes)

    Java6Assertions.assertThat(result.name).isEqualTo("Row")
    Java6Assertions.assertThat(result.abbreviation).isEqualTo("ROW")
    Java6Assertions.assertThat(result.description).isEqualTo("Back squat is now rowing")
    Java6Assertions.assertThat(result.validity).isEqualTo(TestData.NOT_YET_VALID)
  }


}