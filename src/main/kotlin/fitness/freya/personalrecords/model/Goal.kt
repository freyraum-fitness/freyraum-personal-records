package fitness.freya.personalrecords.model

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "goal", schema = "public")
data class Goal(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID?,
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    val exercise: Exercise,
    @Enumerated(EnumType.STRING)
    val unit: Unit,
    @Enumerated(EnumType.STRING)
    val comparingUnit: Unit,
    @Enumerated(EnumType.STRING)
    val winning: Winning,
    val description: String,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "from", column = Column(name = "validity_from")),
        AttributeOverride(name = "to", column = Column(name = "validity_to"))
    )
    val validity: Validity
)