package fitness.freya.personalrecords.mapping

import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.api.exception.ExerciseNotFoundException
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.repository.ExerciseRepository
import org.springframework.stereotype.Component

@Component
class GoalMapper(
    val exerciseRepository: ExerciseRepository,
    val validityMapper: ValidityMapper) {

  fun map(goal: Goal): GoalDto = GoalDto(
      id = goal.id,
      exerciseId = goal.exercise.id!!,
      unit = goal.unit,
      comparingUnit = goal.comparingUnit,
      winning = goal.winning,
      description = goal.description,
      validity = validityMapper.map(goal.validity)
  )

  fun map(goal: GoalDto): Goal = Goal(
      id = goal.id,
      exercise = exerciseRepository.findById(goal.exerciseId)
          .orElseThrow{ ExerciseNotFoundException(goal.exerciseId) },
      unit = goal.unit,
      comparingUnit = goal.comparingUnit,
      winning = goal.winning,
      description = goal.description,
      validity = validityMapper.map(goal.validity)
  )

  fun apply(goal: Goal, changes: GoalDto) = goal.copy(
      exercise = exerciseRepository.findById(changes.exerciseId)
          .orElseThrow{ ExerciseNotFoundException(changes.exerciseId) },
      unit = changes.unit,
      comparingUnit = changes.comparingUnit,
      winning = changes.winning,
      description = changes.description,
      validity = validityMapper.map(changes.validity)
  )
}
