package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.RecordDto
import fitness.freya.personalrecords.model.Measurement
import org.springframework.stereotype.Component

@Component
class RecordMapper(
    val exerciseMapper: ExerciseMapper,
    val goalMapper: GoalMapper,
    val measurementMapper: MeasurementMapper) {

  fun map(measurement: Measurement): RecordDto = RecordDto(
      exercise = exerciseMapper.map(measurement.goal.exercise),
      goal = goalMapper.map(measurement.goal),
      measurement = measurementMapper.map(measurement)
  )

}
