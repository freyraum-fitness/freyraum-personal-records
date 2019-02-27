package fitness.freya.personalrecords.api.exception

import fitness.freya.personalrecords.api.dto.MeasurementDto
import fitness.freya.personalrecords.mapping.MeasurementMapper
import fitness.freya.personalrecords.service.RecordService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/records")
class RecordsController(
    val recordService: RecordService,
    val measurementMapper: MeasurementMapper) {

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/users/{userId}")
  fun measurements(@PathVariable("userId") userId: UUID): List<MeasurementDto> =
      recordService.getAllPersonalRecords(userId).stream()
          .map(measurementMapper::map)
          .collect(Collectors.toList())

}