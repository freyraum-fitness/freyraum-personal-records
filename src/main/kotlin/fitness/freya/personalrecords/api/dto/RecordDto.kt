package fitness.freya.personalrecords.api.dto

data class RecordDto(
    val exercise: ExerciseDto,
    val goal: GoalDto,
    val measurement: MeasurementDto
)
