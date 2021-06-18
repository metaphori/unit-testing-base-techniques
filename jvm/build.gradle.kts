/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.5.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is used by the application.
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.guava:guava:27.1-jre")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")


    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

application {
    // Define the main class for the application
    mainClassName = "it.unibo.testlecture.App"
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
    testLogging.events("failed","passed","skipped")
    // https://github.com/junit-team/junit5/issues/2041
    // Workaround to show display name of parameterized tests
    afterTest(KotlinClosure2<TestDescriptor, TestResult, Any>({ descriptor, result ->
        val test = descriptor as org.gradle.api.internal.tasks.testing.TestDescriptorInternal
        println("\n${test.className} [${test.classDisplayName}] > ${test.name}\n${test.displayName}\n${result.resultType}")
    }))
}
