package fitness.freya.personalrecords.api.dto

import java.time.LocalDate
import java.util.UUID

data class MeasurementDto(
    val id: UUID?,
    val userId: UUID,
    val goalId: UUID,
    val value: Long,
    val comparingValue: Long,
    val date: LocalDate
)