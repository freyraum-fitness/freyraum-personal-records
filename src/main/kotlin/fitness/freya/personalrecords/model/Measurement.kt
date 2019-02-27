package fitness.freya.personalrecords.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "measurement", schema = "public")
data class Measurement(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID?,
    val userId: UUID,
    @ManyToOne
    @JoinColumn(name = "goal_id")
    val goal: Goal,
    val value: Long,
    val comparingValue: Long,
    val date: LocalDate
)