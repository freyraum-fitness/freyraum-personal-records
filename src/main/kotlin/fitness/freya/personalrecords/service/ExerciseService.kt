package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.api.dto.ExerciseDto
import fitness.freya.personalrecords.api.exception.ExerciseNotFoundException
import fitness.freya.personalrecords.mapping.ExerciseMapper
import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.repository.ExerciseRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Service
class ExerciseService(
    val exerciseRepository: ExerciseRepository,
    val exerciseMapper: ExerciseMapper) {

  fun getCurrentExercises(): List<Exercise> {
    val now = LocalDateTime.now()
    return exerciseRepository.findByValidityFromLessThanEqualAndValidityToGreaterThanEqual(now, now)
  }

  fun getExercise(id: UUID): Optional<Exercise> = exerciseRepository.findById(id)

  fun create(exercise: Exercise): Exercise = exerciseRepository.save(exercise)

  fun update(id: UUID, changes: ExerciseDto): Exercise = getExercise(id)
      .map { exercise -> exerciseMapper.apply(exercise, changes) }
      .map { exerciseRepository.save(it) }
      .orElseThrow { throw ExerciseNotFoundException(id) }

}