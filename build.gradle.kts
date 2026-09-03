plugins {
    kotlin("jvm") version "2.4.10"
    id("org.jmailen.kotlinter") version "5.7.0"
}

group = "camp.nextstep.edu"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(25)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
    register("ktlintCheck") {
        group = "verification"
        description = "Runs ktlint on all Kotlin sources."
        dependsOn("lintKotlin")
    }
}

kotlinter {
    ktlintVersion = "1.8.0"
    reporters = arrayOf("plain")
}
