package fitness.freya.personalrecords

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class PersonalRecordsApplication

fun main(args: Array<String>) {
  runApplication<PersonalRecordsApplication>(*args)
}

