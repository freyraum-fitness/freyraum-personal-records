package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.MeasurementDto
import fitness.freya.personalrecords.api.exception.GoalNotFoundException
import fitness.freya.personalrecords.model.Measurement
import fitness.freya.personalrecords.repository.GoalRepository
import org.springframework.stereotype.Component

@Component
class MeasurementMapper(val goalRepository: GoalRepository) {

  fun map(measurement: Measurement): MeasurementDto = MeasurementDto(
      id = measurement.id,
      userId = measurement.userId,
      goalId = measurement.goal.id!!,
      value = measurement.value,
      comparingValue = measurement.value,
      date = measurement.date
  )

  fun map(measurement: MeasurementDto): Measurement = Measurement(
      id = measurement.id,
      userId = measurement.userId,
      goal = goalRepository.findById(measurement.goalId)
          .orElseThrow{ GoalNotFoundException(measurement.goalId) },
      value = measurement.value,
      comparingValue = measurement.value,
      date = measurement.date
  )

  fun apply(measurement: Measurement, changes: MeasurementDto) = measurement.copy(
      userId = changes.userId,
      goal = goalRepository.findById(changes.goalId)
          .orElseThrow{ GoalNotFoundException(changes.goalId) },
      value = changes.value,
      comparingValue = changes.value,
      date = changes.date
  )
}
