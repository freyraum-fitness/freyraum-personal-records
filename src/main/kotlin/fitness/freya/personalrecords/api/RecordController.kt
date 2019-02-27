package fitness.freya.personalrecords.api

import fitness.freya.personalrecords.api.dto.ErrorDto
import fitness.freya.personalrecords.api.dto.RecordDto
import fitness.freya.personalrecords.api.exception.ExerciseNotFoundException
import fitness.freya.personalrecords.api.exception.GoalNotFoundException
import fitness.freya.personalrecords.mapping.RecordMapper
import fitness.freya.personalrecords.service.PreferencesService
import fitness.freya.personalrecords.service.RecordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.security.Principal
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/personalrecords")
class RecordController(
    val preferencesService: PreferencesService,
    val recordService: RecordService,
    val recordMapper: RecordMapper) {

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/users/{userId}")
  fun getAllPersonalRecordsOfUser(
      authentication: Authentication,
      @PathVariable("userId") userId: UUID): List<RecordDto> {
    preferencesService.assertAccessAllowed(authentication, "VIEW_STATISTICS", userId)

    return recordService.getAllPersonalRecords(userId).stream()
        .map(recordMapper::map)
        .collect(Collectors.toList())
  }


}