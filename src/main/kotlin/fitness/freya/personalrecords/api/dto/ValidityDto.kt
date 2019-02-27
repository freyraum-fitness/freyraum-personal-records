package fitness.freya.personalrecords.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ValidityDto(
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val from: LocalDateTime?,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val to: LocalDateTime?
)