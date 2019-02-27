package fitness.freya.personalrecords.config

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import java.net.Socket
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.sql.DataSource
import javax.net.ssl.SSLEngine
import javax.net.ssl.TrustManager
import javax.net.ssl.X509ExtendedTrustManager

@Configuration
class AppConfig(
    @Value("\${spring.datasource.driverClassName:org.postgresql.Driver}")
    private val driverClassName: String,
    @Value("\${DB_URL:jdbc:postgresql://localhost/freyraum-exercise}")
    private val dataSourceUrl: String,
    @Value("\${DB_USR:postgres}")
    private val dataSourceUsername: String,
    @Value("\${DB_PSW:postgres}")
    private val dataSourcePassword: String) {

  private val LOGGER = LogManager.getLogger(AppConfig::class.java)

  companion object {
    init {
      val trustAllCerts = arrayOf<TrustManager>(object : X509ExtendedTrustManager() {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
          return null
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String, socket: Socket) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String, socket: Socket) {
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String, sslEngine: SSLEngine) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String, sslEngine: SSLEngine) {
        }
      })

      val sslContext = SSLContext.getInstance("SSL")
      sslContext.init(null, trustAllCerts, java.security.SecureRandom())
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
    }
  }

  @Bean
  fun dataSource(): DataSource {
    LOGGER.info("Connecting to: {}, {}:{}", dataSourceUrl, dataSourceUsername, dataSourcePassword)

    val dataSource = DriverManagerDataSource()
    dataSource.setDriverClassName(driverClassName)
    dataSource.url = dataSourceUrl
    dataSource.username = dataSourceUsername
    dataSource.password = dataSourcePassword
    return dataSource
  }

  fun configure(http: HttpSecurity) {
    http.antMatcher("/**")
        .authorizeRequests()
        .anyRequest()
        .authenticated()
  }

}