package fitness.freya.personalrecords.client

import feign.Headers
import feign.Param
import feign.RequestLine
import fitness.freya.personalrecords.api.dto.PreferencesDto
import java.util.UUID

@Headers("Content_type: application/json")
interface PreferencesProxy {

  @RequestLine("GET /preferences/{id}/{key}")
  fun getUserPreference(
      @Param(value = "id") id: UUID,
      @Param(value = "string") key: String): PreferencesDto

}