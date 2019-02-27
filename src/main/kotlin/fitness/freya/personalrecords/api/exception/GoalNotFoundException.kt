package fitness.freya.personalrecords.api.exception

import java.util.UUID

class GoalNotFoundException(id: UUID) : RuntimeException(
    String.format("No goal found for id '%s'", id))