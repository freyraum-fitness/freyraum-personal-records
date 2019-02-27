package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.ValidityDto
import fitness.freya.personalrecords.model.Validity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
class ValidityMapperTest {

  @InjectMocks
  lateinit var testee: ValidityMapper

  @Test
  fun `should map Validity to ValidityDto`() {
    // given
    val from = LocalDateTime.of(
        LocalDate.of(2018, 11, 1),
        LocalTime.of(10, 30)
    )
    val to = LocalDateTime.of(
        LocalDate.of(2018, 12, 31),
        LocalTime.of(21, 45)
    )
    val validity = Validity(from, to)

    // when
    val result = testee.map(validity)

    // then
    assertThat(result.from, equalTo(from))
    assertThat(result.to, equalTo(to))
  }

  @Test
  fun `should map ValidityDto to Validity`() {
    // given
    val from = LocalDateTime.of(
        LocalDate.of(2018, 11, 1),
        LocalTime.of(10, 30)
    )
    val to = LocalDateTime.of(
        LocalDate.of(2018, 12, 31),
        LocalTime.of(21, 45)
    )
    val validity = ValidityDto(from, to)

    // when
    val result = testee.map(validity)

    // then
    assertThat(result.from, equalTo(from))
    assertThat(result.to, equalTo(to))
  }

}