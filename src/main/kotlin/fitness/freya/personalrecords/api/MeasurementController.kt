package fitness.freya.personalrecords.api

import fitness.freya.personalrecords.api.dto.MeasurementDto
import fitness.freya.personalrecords.mapping.MeasurementMapper
import fitness.freya.personalrecords.service.MeasurementService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Collections
import java.util.UUID

@RestController
@RequestMapping("/measurements")
class MeasurementController(
    val measurementService: MeasurementService,
    val measurementMapper: MeasurementMapper) {

  @PreAuthorize("hasAnyAuthority('USER', 'TRAINER', 'ADMIN')")
  @GetMapping("/users/{userId}/exercises/{exerciseId}")
  fun measurements(@PathVariable("userId") userId: UUID,
                   @PathVariable("exerciseId") exerciseId: UUID): List<MeasurementDto> =
      Collections.emptyList()

}