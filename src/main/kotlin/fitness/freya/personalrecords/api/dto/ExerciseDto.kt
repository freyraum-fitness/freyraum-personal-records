package fitness.freya.personalrecords.api.dto

import java.util.UUID

data class ExerciseDto(
    val id: UUID?,
    val name: String,
    val description: String,
    val abbreviation: String,
    val validity: ValidityDto
)