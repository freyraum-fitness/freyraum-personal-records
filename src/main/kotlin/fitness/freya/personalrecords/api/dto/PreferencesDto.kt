package fitness.freya.personalrecords.api.dto

import java.util.UUID

data class PreferencesDto(
    val id: UUID,
    val userId: UUID,
    val key: String,
    val value: String?
)