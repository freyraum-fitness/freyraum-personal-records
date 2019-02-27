package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.ExerciseDto
import fitness.freya.personalrecords.model.Exercise
import org.springframework.stereotype.Component

@Component
class ExerciseMapper(val validityMapper: ValidityMapper) {

  fun map(exercise: Exercise): ExerciseDto = ExerciseDto(
      id = exercise.id,
      name = exercise.name,
      abbreviation = exercise.abbreviation,
      description = exercise.description,
      validity = validityMapper.map(exercise.validity)
  )

  fun map(exercise: ExerciseDto): Exercise = Exercise(
      id = exercise.id,
      name = exercise.name,
      abbreviation = exercise.abbreviation,
      description = exercise.description,
      validity = validityMapper.map(exercise.validity)
  )

  fun apply(exercise: Exercise, changes: ExerciseDto) = exercise.copy(
      name = changes.name,
      abbreviation = changes.abbreviation,
      description = changes.description,
      validity = validityMapper.map(changes.validity)
  )
}
