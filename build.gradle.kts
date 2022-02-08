plugins {
    id("java-gradle-plugin")
    `kotlin-dsl`
    id("maven-publish")

    // just for example. In real plugin it's remove
    kotlin("jvm") version "1.5.30"
}

version = "1.0.0"
group = "mu.plugin"

gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS_FULL


repositories {
    mavenCentral()
    gradlePluginPortal()
}

val integrationTest: SourceSet by sourceSets.creating

gradlePlugin {
    testSourceSets(integrationTest, sourceSets.test.get())
    plugins {
        create("plugin") {
            id = "my.plugin"
            implementationClass = "plugin.MyPlugin"
        }
    }
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {

    implementation(
        group = "io.gitlab.arturbosch.detekt", name = "detekt-gradle-plugin", version = "1.19.0"
    )

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.7.0")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.7.0")
    testImplementation(group = "io.mockk", name = "mockk", version = "1.10.5")
}



fun DependencyHandlerScope.integrationTestImplementation(notation: Any) {
    "integrationTestImplementation"(notation)
}

fun DependencyHandlerScope.integrationTestImplementation(
    group: String,
    name: String,
    version: String? = null
) {
    "integrationTestImplementation"(group, name, version)
}

dependencies {
    integrationTestImplementation(gradleTestKit())
    integrationTestImplementation(group = "io.github.microutils", name = "kotlin-logging", version = "2.0.4")
    integrationTestImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.7.0")
    integrationTestImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.7.0")
    integrationTestImplementation(group = "io.mockk", name = "mockk", version = "1.10.5")
    integrationTestImplementation(group = "org.hamcrest", name = "hamcrest-all", version = "1.3")
    integrationTestImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = "4.4.3")
    integrationTestImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = "4.4.3")
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = integrationTest.output.classesDirs
    classpath = integrationTest.runtimeClasspath
    mustRunAfter(tasks.test)
    useJUnitPlatform {
        reports.html.isEnabled = false
    }
    dependsOn(tasks.publishToMavenLocal)
}


