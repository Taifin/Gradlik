plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":gradlik-dsl"))
}

tasks.test {
    useJUnitPlatform()
}