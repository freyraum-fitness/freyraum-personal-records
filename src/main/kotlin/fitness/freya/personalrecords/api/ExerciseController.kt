package fitness.freya.personalrecords.api

import fitness.freya.personalrecords.api.dto.ErrorDto
import fitness.freya.personalrecords.api.dto.ExerciseDto
import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.api.exception.ExerciseNotFoundException
import fitness.freya.personalrecords.mapping.ExerciseMapper
import fitness.freya.personalrecords.mapping.GoalMapper
import fitness.freya.personalrecords.service.ExerciseService
import fitness.freya.personalrecords.service.GoalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/exercises")
class ExerciseController(
    val exerciseService: ExerciseService,
    val goalService: GoalService,
    val exerciseMapper: ExerciseMapper,
    val goalMapper: GoalMapper) {

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping
  fun currentExercises(): List<ExerciseDto> =
      exerciseService.getCurrentExercises().stream()
          .map(exerciseMapper::map)
          .collect(Collectors.toList())

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  fun createExercise(@RequestBody exerciseDto: ExerciseDto): ExerciseDto {
    val exercise = exerciseMapper.map(exerciseDto)
    val created = exerciseService.create(exercise)
    return exerciseMapper.map(created)
  }

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/{id}")
  fun getExercise(@PathVariable("id") id: UUID): ExerciseDto =
      exerciseService.getExercise(id)
          .map(exerciseMapper::map)
          .orElseThrow { throw ExerciseNotFoundException(id) }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/{id}")
  fun updateExercise(@PathVariable("id") id: UUID, @RequestBody exerciseDto: ExerciseDto): ExerciseDto {
    val updated = exerciseService.update(id, exerciseDto)
    return exerciseMapper.map(updated)
  }

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/{id}/goals")
  fun getGoalsForExcercise(@PathVariable("id") id: UUID): List<GoalDto> =
      goalService.getGoalsForExercise(id).stream()
          .map(goalMapper::map)
          .collect(Collectors.toList())

}