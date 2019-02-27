package fitness.freya.personalrecords.utils

import io.restassured.RestAssured
import io.restassured.http.Header
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders

class FreyRaumWireMockRuleTest {

  private val rule = FreyRaumWireMockRule().withAuthenticatedUser()

  @BeforeEach
  fun `start WireMock`() {
    rule.start()
  }

  @AfterEach
  fun `stop WireMock`() {
    rule.stop()
  }

  @Test
  fun `return tobi tester`() {
    val givenRequest = RestAssured
        .given()
        .relaxedHTTPSValidation()
        .baseUri("https://localhost")
        .port(9443)
        .header(Header(HttpHeaders.AUTHORIZATION, "Bearer xyz123"))

    val response = givenRequest.get("/user/me")

    response
        .then()
        .statusCode(200)
        .body(
            CoreMatchers.containsString("USER"),
            CoreMatchers.containsString("tobi.tester")
        )
  }

}