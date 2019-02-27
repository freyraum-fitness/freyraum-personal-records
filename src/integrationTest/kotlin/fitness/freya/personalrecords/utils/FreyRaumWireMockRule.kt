package fitness.freya.personalrecords.utils

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule

class FreyRaumWireMockRule : WireMockRule(
    wireMockConfig()
        .bindAddress("localhost")
        .httpsPort(9443)
) {

  fun authorize(): FreyRaumWireMockRule {
    this.stubFor(get("/oauth/authorize").willReturn(
        okJson("")
    ))
    return this
  }

  fun withAuthenticatedUser(): FreyRaumWireMockRule {
    authorize()
    this.stubFor(get("/user/me").willReturn(
        okJson("{" +
            "\"username\":\"tobi.tester@freya.fitness\"," +
            "\"authorities\":[{\"authority\":\"USER\"}]" +
            "}")))

    return this
  }

  fun withAuthenticatedTrainer(): FreyRaumWireMockRule {
    authorize()
    this.stubFor(get("/user/me").willReturn(
        okJson("{" +
            "\"username\":\"tobi.tester@freya.fitness\"," +
            "\"authorities\":[{\"authority\":\"TRAINER\"}]" +
            "}")))

    return this
  }

  fun withAuthenticatedAdmin(): FreyRaumWireMockRule {
    authorize()
    this.stubFor(get("/user/me").willReturn(
        okJson("{" +
            "\"username\":\"tobi.tester@freya.fitness\"," +
            "\"authorities\":[{\"authority\":\"ADMIN\"}]" +
            "}")))

    return this
  }

}
