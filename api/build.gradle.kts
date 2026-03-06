plugins {
    id("org.openapi.generator") version "7.20.0"
}
sourceSets {
    main {
        java {
            srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
        }
        java {
            srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/resources")
        }
    }
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$projectDir/openapi.yaml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("com.polbohub.api")
    modelPackage.set("com.polbohub.api.model")

    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true",
        "openApiNullable" to "false",
        "generateConstructorWithAllArgs" to "true",
        "generatedConstructorWithAllArgs" to "true",
        "useBeanValidation" to "true"
    ))
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.swagger.core.v3:swagger-annotations:2.2.44")
}


