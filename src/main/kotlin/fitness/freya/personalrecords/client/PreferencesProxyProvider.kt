package fitness.freya.personalrecords.client

import feign.Feign
import feign.auth.BasicAuthRequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
data class PreferencesProxyProvider(
    @Value("\${clients.preferences.url})")
    private val url: String,
    @Value("\${clients.preferences.user}")
    private val user: String,
    @Value("\${clients.preferences.password}")
    private val password: String) {

  @Bean
  fun preferencesProxy(): PreferencesProxy {
    return Feign.builder()
        .requestInterceptor(BasicAuthRequestInterceptor(user, password))
        .target(PreferencesProxy::class.java, url)
  }

}