package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.model.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface GoalRepository : JpaRepository<Goal, UUID> {

  fun findByExerciseId(id: UUID): List<Goal>

  fun findByExercise(exercise: Exercise): List<Goal>

  fun findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(
      from: LocalDateTime, to: LocalDateTime): List<Goal>

}