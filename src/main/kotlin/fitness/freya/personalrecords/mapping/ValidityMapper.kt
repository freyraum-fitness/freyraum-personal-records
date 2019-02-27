package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.ValidityDto
import fitness.freya.personalrecords.model.Validity
import org.springframework.stereotype.Component

@Component
class ValidityMapper {

  fun map(validity: Validity): ValidityDto = ValidityDto(validity.from, validity.to)

  fun map(validity: ValidityDto?): Validity = Validity(validity?.from, validity?.to)

}
