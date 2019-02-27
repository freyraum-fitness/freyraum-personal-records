package fitness.freya.personalrecords.model

import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
data class Validity(
    val from: LocalDateTime?,
    val to: LocalDateTime?)