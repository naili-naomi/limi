plugins {
    kotlin("jvm") version "1.9.0"
    application
    kotlin("plugin.serialization") version "1.9.0"
}

group = "limi"
version = "1.0"


repositories {
    mavenCentral()
}

val ktorVersion = "2.3.3"
val logbackVersion = "1.5.13"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion") // versão conforme a usada
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("junit:junit:4.13.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.ktor:ktor-server-test-host:${ktorVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    implementation("org.valiktor:valiktor-core:0.12.0")
    implementation("org.valiktor:valiktor-javatime:0.12.0")
    implementation("io.ktor:ktor-server-auth:${ktorVersion}")
    implementation("io.ktor:ktor-server-auth-jwt:${ktorVersion}")
    implementation("com.auth0:java-jwt:${ktorVersion}")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("javax.mail:mail:1.4.7")
    implementation("javax.mail:mail:1.4.7")



    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("com.sun.mail:jakarta.mail:2.0.1")
    implementation("javax.mail:mail:1.4.7")


}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.limi.ApplicationKt")
}