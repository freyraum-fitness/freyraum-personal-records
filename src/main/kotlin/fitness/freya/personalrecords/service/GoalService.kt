package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.api.exception.GoalNotFoundException
import fitness.freya.personalrecords.mapping.GoalMapper
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.repository.GoalRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Service
class GoalService(
    val goalRepository: GoalRepository,
    val goalMapper: GoalMapper) {

  fun getCurrentGoals(): List<Goal> {
    val now = LocalDateTime.now()
    return goalRepository.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)
  }

  fun getGoal(id: UUID): Optional<Goal> = goalRepository.findById(id)

  fun create(goal: Goal): Goal = goalRepository.save(goal)

  fun update(id: UUID, changes: GoalDto): Goal = getGoal(id)
      .map { goal -> goalMapper.apply(goal, changes) }
      .map { goalRepository.save(it) }
      .orElseThrow { throw GoalNotFoundException(id) }

  fun getGoalsForExercise(id: UUID): List<Goal> = goalRepository.findByExerciseId(id)

}