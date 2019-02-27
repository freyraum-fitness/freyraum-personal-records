package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.model.Measurement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MeasurementRepository : JpaRepository<Measurement, UUID> {

  fun findByUserId(id: UUID): List<Measurement>

}