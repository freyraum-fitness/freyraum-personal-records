package fitness.freya.personalrecords.model

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "exercise", schema = "public")
data class Exercise(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID?,
    val name: String,
    val description: String,
    val abbreviation: String,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "from", column = Column(name = "validity_from")),
        AttributeOverride(name = "to", column = Column(name = "validity_to"))
    )
    val validity: Validity
)