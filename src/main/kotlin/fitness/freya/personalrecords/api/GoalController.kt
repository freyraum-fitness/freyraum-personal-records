package fitness.freya.personalrecords.api

import fitness.freya.personalrecords.api.dto.ErrorDto
import fitness.freya.personalrecords.api.dto.GoalDto
import fitness.freya.personalrecords.api.exception.GoalNotFoundException
import fitness.freya.personalrecords.mapping.GoalMapper
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
@RequestMapping("/goals")
class GoalController(
    val goalService: GoalService,
    val goalMapper: GoalMapper) {

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping
  fun currentGoals(): List<GoalDto> = goalService.getCurrentGoals().stream()
      .map(goalMapper::map)
      .collect(Collectors.toList())

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  fun createGoal(@RequestBody goalDto: GoalDto): GoalDto {
    val goal = goalMapper.map(goalDto)
    val created = goalService.create(goal)
    return goalMapper.map(created)
  }

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/{id}")
  fun getGoal(@PathVariable("id") id: UUID): GoalDto = goalService.getGoal(id)
      .map(goalMapper::map)
      .orElseThrow { throw GoalNotFoundException(id) }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/{id}")
  fun updateGoal(@PathVariable("id") id: UUID, @RequestBody goalDto: GoalDto): GoalDto {
    val updated = goalService.update(id, goalDto)
    return goalMapper.map(updated)
  }

}