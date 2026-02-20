plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.polbohub"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":persistence"))
    implementation(project(":api"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    testImplementation("org.springframework.security:spring-security-test")
}

apply(from = "build-modules.gradle.kts")

tasks.withType<Test> {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false
}

tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = true
}


