plugins {
    id("java")
}

group = "me.perwollnt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("org.reflections:reflections:0.10.2")

    implementation("io.github.cdimascio:dotenv-java:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}