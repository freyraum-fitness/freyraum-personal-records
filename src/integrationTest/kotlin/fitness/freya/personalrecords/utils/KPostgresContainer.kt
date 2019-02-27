package fitness.freya.personalrecords.utils

import org.testcontainers.containers.PostgreSQLContainer

class KPostgresContainer(imageName: String) : PostgreSQLContainer<KPostgresContainer>(imageName)