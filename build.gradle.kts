import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

val kotlinVersion: String by extra("1.9.0")
val kotlinCoroutinesVersion: String by extra("1.7.0-RC")

allprojects {
    repositories {
        mavenCentral()
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.compilerArgs.add("-parameters")
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs = listOf("-java-parameters")
            }
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

