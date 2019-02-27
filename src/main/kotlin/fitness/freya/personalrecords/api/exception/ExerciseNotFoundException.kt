package fitness.freya.personalrecords.api.exception

import java.util.UUID

class ExerciseNotFoundException(id: UUID) : RuntimeException(
    String.format("No exercise found for id '%s'", id))