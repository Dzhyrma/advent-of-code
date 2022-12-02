plugins {
    kotlin("jvm") version "1.7.20"
}

group = "org.adventofcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
