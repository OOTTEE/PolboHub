subprojects {

    repositories {
        mavenCentral()
    }

    pluginManager.apply("java")
    pluginManager.apply("org.springframework.boot")
    pluginManager.apply("io.spring.dependency-management")

    pluginManager.withPlugin("java") {
        val javaExtension = extensions.getByName("java") as JavaPluginExtension
        javaExtension.toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }

    dependencies {
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }

    tasks.named("bootJar") {
        enabled = false
    }

    tasks.named("jar") {
        enabled = true
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}