package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.client.PreferencesProxy
import org.apache.logging.log4j.LogManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.UUID

@Service
data class PreferencesService(
    val preferencesProxy: PreferencesProxy) {

  companion object {
    private val LOGGER = LogManager.getLogger(PreferencesService::class.java)
  }

  fun assertAccessAllowed(authentication: Authentication, preferencesKey: String, userId: UUID) {
    if (!isAccessAllowed(authentication, preferencesKey, userId)) {
      throw IllegalAccessException("You are not allowed to access this information")
    }
  }

  fun isAccessAllowed(authentication: Authentication, preferencesKey: String, userId: UUID): Boolean {
    LOGGER.debug("check user preference '{}' of user '{}'", preferencesKey, userId)
    if (isAdmin(authentication)) {
      LOGGER.debug("{} has admin role -> access allowed", authentication.credentials)
      return true
    }
    val preference = preferencesProxy.getUserPreference(userId, preferencesKey)
    LOGGER.debug("{} - {}: {}", userId, preferencesKey, preference.value)
    return preference.value === "true"
  }

  fun isAdmin(authentication: Authentication): Boolean {
    val authorities = authentication.authorities
    return authorities.contains(SimpleGrantedAuthority("ADMIN"))
  }

}