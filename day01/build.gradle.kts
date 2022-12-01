plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("de.nimelrian.aoc2021.MainKt")
}
