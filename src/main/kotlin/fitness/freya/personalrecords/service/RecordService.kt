package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.model.Measurement
import fitness.freya.personalrecords.repository.MeasurementRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RecordService(
    val measurementService: MeasurementService) {

  fun getAllPersonalRecords(userId: UUID): List<Measurement> {
    val result = ArrayList<Measurement>()
    val group =
        measurementService.findByUserId(userId).groupBy { it.goal }
    for ((_, measurements) in group) {
      result.add(findBestMeasurement(measurements))
    }
    return result
  }

  private fun findBestMeasurement(measurements: List<Measurement>): Measurement {
    val goal = measurements[0].goal
    val winning = goal.winning
    val comparator = winning.comparator()
    return measurements.stream()
        .sorted{ m1, m2 -> comparator.compare(m2.comparingValue, m1.comparingValue) }
        .findFirst()
        .get()
  }

}