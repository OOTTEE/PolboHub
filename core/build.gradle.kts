dependencies {
    implementation(project(":domain"))
    implementation(project(":api"))
    implementation(project(":persistence"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.jsoup:jsoup:1.18.3")
}