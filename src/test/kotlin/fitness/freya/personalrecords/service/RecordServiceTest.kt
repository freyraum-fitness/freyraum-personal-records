package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.model.Goal
import fitness.freya.personalrecords.model.Measurement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.ArrayList
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class RecordServiceTest(
    @Mock var measurementService: MeasurementService) {

  @InjectMocks
  lateinit var testee: RecordService

  val measurements = ArrayList<Measurement>()

  @BeforeEach
  fun `set up`() {
    `when`(measurementService.findByUserId(TestData.TEST_USER_1)).thenReturn(measurements)
  }

  @Test
  fun `get measurement with highest comparing value`() {
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 70)
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 80)
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 75)

    val measurements = testee.getAllPersonalRecords(TestData.TEST_USER_1)

    assertThat(measurements).hasSize(1)
    assertThat(measurements[0].goal).isEqualTo(TestData.BACK_SQUAT_MAX_WEIGHT)
    assertThat(measurements[0].comparingValue).isEqualTo(80)
  }

  @Test
  fun `get measurements for all goals`() {
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 70)
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 80)
    givenMeasurement(goal = TestData.BACK_SQUAT_MAX_WEIGHT, value = 1, comparingValue = 90)
    givenMeasurement(goal = TestData.ROW_FASTEST, value = 1000, comparingValue = 180)
    givenMeasurement(goal = TestData.ROW_CALORIES, value = 60, comparingValue = 300)

    val measurements = testee.getAllPersonalRecords(TestData.TEST_USER_1)

    assertThat(measurements)
        .hasSize(3)
        .extracting("goal")
        .containsExactlyInAnyOrder(
            TestData.BACK_SQUAT_MAX_WEIGHT,
            TestData.ROW_FASTEST,
            TestData.ROW_CALORIES)
  }

  private fun givenMeasurement(goal: Goal, value: Long, comparingValue: Long) {
    measurements.add(
        Measurement(
            id = UUID.randomUUID(),
            userId = TestData.TEST_USER_1,
            goal = goal,
            value = value,
            comparingValue = comparingValue,
            date = LocalDate.now())
    )
  }

}