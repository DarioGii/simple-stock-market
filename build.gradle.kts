plugins {
    val pluginVersion = "1.9.23"

    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version pluginVersion
    kotlin("plugin.spring") version pluginVersion
}

group = "co.uk.darioghunneyware.citibank"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:3.2.3")
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.shell:spring-shell-starter")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.shell:spring-shell-test")
    testImplementation("org.springframework.shell:spring-shell-test-autoconfigure")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}
