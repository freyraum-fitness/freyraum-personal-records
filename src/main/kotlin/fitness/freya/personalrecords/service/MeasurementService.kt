package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.model.Measurement
import fitness.freya.personalrecords.repository.MeasurementRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MeasurementService(
    val measurementRepository: MeasurementRepository) {

  fun findByUserId(userId: UUID): List<Measurement> =
      measurementRepository.findByUserId(userId)

}