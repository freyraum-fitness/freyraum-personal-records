package fitness.freya.personalrecords.api.dto

import fitness.freya.personalrecords.model.Winning
import fitness.freya.personalrecords.model.Unit
import java.util.UUID

data class GoalDto(
    val id: UUID?,
    val exerciseId: UUID,
    val unit: Unit,
    val comparingUnit: Unit,
    val winning: Winning,
    val description: String,
    val validity: ValidityDto
)