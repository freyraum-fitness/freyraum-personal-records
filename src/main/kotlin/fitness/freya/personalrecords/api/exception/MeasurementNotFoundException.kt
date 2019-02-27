package fitness.freya.personalrecords.api.exception

import java.util.UUID

class MeasurementNotFoundException(id: UUID) : RuntimeException(
    String.format("No measurement found for id '%s'", id))