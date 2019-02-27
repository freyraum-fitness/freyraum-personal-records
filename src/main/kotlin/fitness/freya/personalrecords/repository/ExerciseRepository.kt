package fitness.freya.personalrecords.repository

import fitness.freya.personalrecords.model.Exercise
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface ExerciseRepository : JpaRepository<Exercise, UUID> {

  fun findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(
      from: LocalDateTime, to: LocalDateTime): List<Exercise>

}