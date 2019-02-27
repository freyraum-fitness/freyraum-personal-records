package fitness.freya.personalrecords

import fitness.freya.personalrecords.model.Exercise
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.model.Unit
import fitness.freya.personalrecords.model.Validity
import fitness.freya.personalrecords.model.Winning
import java.time.LocalDateTime
import java.util.UUID

class TestData {
  companion object {
    val VALID = Validity(LocalDateTime.now().minusYears(10), LocalDateTime.now().plusYears(10))
    val NOT_YET_VALID = Validity(LocalDateTime.now().plusDays(10), LocalDateTime.now().plusYears(10))
    val NOT_ANYMORE_VALID = Validity(LocalDateTime.now().minusYears(10), LocalDateTime.now().minusDays(10))

    val TEST_USER_1 = UUID.fromString("4560c6d8-4afd-4145-b1f4-cd216ddaaa32")
    val TEST_USER_2 = UUID.fromString("21ab5b9b-12fe-4287-bcf2-47da3f286982")

    val BENCH_PRESS = Exercise(
        id = UUID.fromString("f4f4dae4-7394-484b-9777-48f37d61d742"),
        name = "Beanch press",
        abbreviation = "BP",
        description = "the classic",
        validity = VALID
    )
    val BENCH_PRESS_MAX_WEIGHT = Goal(
        id = UUID.fromString("f6074cf2-1c06-405a-beb0-9af10f72d0b4"),
        exercise = BENCH_PRESS,
        description = "push as much as you can for a given number of reps",
        unit = Unit.REPETITIONS,
        comparingUnit = Unit.KILOGRAMS,
        winning = Winning.MORE,
        validity = VALID
    )

    val ROW = Exercise(
        id = UUID.fromString("af0d303a-17a6-4371-bcef-941582d9eb80"),
        name = "Rowing",
        abbreviation = "ROW",
        description = "use the rowing machine and enjoy the view",
        validity = VALID
    )
    val ROW_FASTEST = Goal(
        id = UUID.fromString("2e7cb209-07d0-4f5d-b7f8-05052d92046c"),
        exercise = ROW,
        description = "finish a given distance as fast as you can",
        unit = Unit.METERS,
        comparingUnit = Unit.SECONDS,
        winning = Winning.LESS,
        validity = VALID
    )
    val ROW_CALORIES = Goal(
        id = UUID.fromString("8995f71b-1c01-42d3-b80f-6553966d04af"),
        exercise = ROW,
        description = "how many ckal can you do in 1 minute",
        unit = Unit.SECONDS,
        comparingUnit = Unit.CALORIES,
        winning = Winning.MORE,
        validity = VALID
    )

    val BACK_SQUAT = Exercise(
        id = UUID.fromString("2480aecc-5417-4ec4-bf26-0240f424cd9e"),
        name = "Back Squat",
        abbreviation = "BS",
        description = "the classic",
        validity = VALID
    )
    val BACK_SQUAT_MAX_WEIGHT = Goal(
        id = UUID.fromString("1d9cc13f-a32f-4989-8753-cc3b38daa00a"),
        exercise = BACK_SQUAT,
        description = "squat as much as you can for a given number of reps",
        unit = Unit.REPETITIONS,
        comparingUnit = Unit.KILOGRAMS,
        winning = Winning.MORE,
        validity = VALID
    )
  }
}