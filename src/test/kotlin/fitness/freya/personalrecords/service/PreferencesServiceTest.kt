package fitness.freya.personalrecords.service

import fitness.freya.personalrecords.TestData
import fitness.freya.personalrecords.api.dto.PreferencesDto
import fitness.freya.personalrecords.client.PreferencesProxy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Arrays
import java.util.Collections
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class PreferencesServiceTest(
    @Mock val preferencesProxy: PreferencesProxy) {

  @InjectMocks
  lateinit var testee: PreferencesService

  companion object {
    private const val REQUESTED_KEY: String = "REQUESTED_KEY"
    private val USER_ALLOWS_ACCESS: UUID = TestData.TEST_USER_1
    private val USER_DOES_NOT_ALLOW_ACCESS: UUID = TestData.TEST_USER_2
    private val CURRENT_AUTHORITY: Authentication = mock(Authentication::class.java)
  }

  @BeforeEach
  fun `set up preferences proxy`() {
    `when`(preferencesProxy.getUserPreference(USER_ALLOWS_ACCESS, REQUESTED_KEY))
        .thenReturn(PreferencesDto(id = UUID.randomUUID(), userId = USER_ALLOWS_ACCESS, key = REQUESTED_KEY, value = "true"))
    `when`(preferencesProxy.getUserPreference(USER_DOES_NOT_ALLOW_ACCESS, REQUESTED_KEY))
        .thenReturn(PreferencesDto(id = UUID.randomUUID(), userId = USER_DOES_NOT_ALLOW_ACCESS, key = REQUESTED_KEY, value = "false"))
  }

  @Test
  fun `is not admin with only USER authority`() {
    `when`(CURRENT_AUTHORITY.authorities).thenReturn(Collections.singletonList(SimpleGrantedAuthority("USER")))

    assertThat(testee.isAdmin(CURRENT_AUTHORITY)).isFalse()
  }

  @Test
  fun `is admin with ADMIN authority`() {
    `when`(CURRENT_AUTHORITY.authorities).thenReturn(Collections.singletonList(SimpleGrantedAuthority("ADMIN")))

    assertThat(testee.isAdmin(CURRENT_AUTHORITY)).isTrue()
  }

  @Test
  fun `is admin when one authority is ADMIN`() {
    `when`(CURRENT_AUTHORITY.authorities).thenReturn(Arrays.asList(
        SimpleGrantedAuthority("USER"),
        SimpleGrantedAuthority("ADMIN")))

    assertThat(testee.isAdmin(CURRENT_AUTHORITY)).isTrue()
  }

  @Nested
  @DisplayName("User is admin")
  inner class UserIsAdmin {

    @BeforeEach
    fun `current user is admin`() {
      `when`(CURRENT_AUTHORITY.authorities).thenReturn(Collections.singletonList(SimpleGrantedAuthority("ADMIN")))
    }

    @Test
    fun `requested user allows access`() {
      val accessAllowed = testee.isAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_ALLOWS_ACCESS)

      assertThat(accessAllowed).isTrue()
    }

    @Test
    fun `requested user does not allow access - ADMIN has access anyways`() {
      val accessAllowed = testee.isAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_DOES_NOT_ALLOW_ACCESS)

      assertThat(accessAllowed).isTrue()
    }

    @Test
    fun `access assertion is always successful for ADMIN`() {
      testee.assertAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_DOES_NOT_ALLOW_ACCESS)
    }
  }

  @Nested
  @DisplayName("User is not admin")
  inner class UserIsNotAdmin {

    @BeforeEach
    fun `current user is not admin`() {
      `when`(CURRENT_AUTHORITY.authorities).thenReturn(Collections.singletonList(SimpleGrantedAuthority("USER")))
    }

    @Test
    fun `requested user allows access`() {
      val accessAllowed = testee.isAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_ALLOWS_ACCESS)

      assertThat(accessAllowed).isTrue()
    }

    @Test
    fun `requested user does not allow access - USER has no access`() {
      val accessAllowed = testee.isAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_DOES_NOT_ALLOW_ACCESS)

      assertThat(accessAllowed).isFalse()
    }

    @Test
    fun `access assertion throws exception`() {
      assertThatExceptionOfType(IllegalAccessException::class.java).isThrownBy {
        testee.assertAccessAllowed(CURRENT_AUTHORITY, REQUESTED_KEY, USER_DOES_NOT_ALLOW_ACCESS)
      }
    }
  }

}