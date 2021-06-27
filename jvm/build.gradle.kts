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

    // JaCoCo plugin for test coverage
    jacoco
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    val jupiterVersion = "5.4.2"
    val junitPlatformVersion = "1.7.2"
    val vintageVersion = "5.4.2"

    // These dependencies is used by the application.
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.guava:guava:27.1-jre")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")
    testImplementation("org.junit.platform:junit-platform-suite-api:$junitPlatformVersion") // @SelectClasses
    testImplementation("org.junit.platform:junit-platform-runner:$junitPlatformVersion") // RunWith, JUnitPlatform runner

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$vintageVersion")

    testImplementation("org.slf4j:slf4j-api:1.7.+")
    testRuntimeOnly("org.slf4j:slf4j-log4j12:1.7.+")
}

application {
    // Define the main class for the application
    mainClassName = "it.unibo.testlecture.App"
}

val test by tasks.getting(Test::class) {
    // useJUnit() // JUnit 4 runner infrastructure
    // useJUnitPlatform() // enable JUnit Platform (aka JUnit 5) support
    // useJUnitPlatform { includeEngines("junit-vintage")  } // JUnit 4 tests on JUnit Platform
    useJUnitPlatform {
        includeEngines("junit-vintage", "junit-jupiter")  // JUnit 4 + JUnit 5
        excludeTags("meta")
    }

    testLogging.events("failed","passed","skipped")
}

tasks.register("paramtests", Test::class) {
    // useJUnit() // JUnit 4 runner infrastructure
    // useJUnitPlatform() // enable JUnit Platform (aka JUnit 5) support
    // useJUnitPlatform { includeEngines("junit-vintage")  } // JUnit 4 tests on JUnit Platform
    useJUnitPlatform {
        includeEngines("junit-vintage", "junit-jupiter")  // JUnit 4 + JUnit 5
        excludeTags("meta")
    }

    testLogging.events("failed","passed","skipped")
    // https://github.com/junit-team/junit5/issues/2041
    // Workaround to show display name of parameterized tests
    afterTest(KotlinClosure2<TestDescriptor, TestResult, Any>({ descriptor, result ->
        val test = descriptor as org.gradle.api.internal.tasks.testing.TestDescriptorInternal
        println("${test.className} [${test.classDisplayName}] > ${test.name}\n${test.displayName}\n${result.resultType}")
    }))
}

tasks.register("metatest", Test::class) {
    useJUnitPlatform {
        includeEngines("junit-vintage", "junit-jupiter")  // JUnit 4 + JUnit 5
        includeTags("meta")
    }

    testLogging.events("failed","passed","skipped")
}

// ./gradlew test --rerun-tasks
// https://docs.gradle.org/current/userguide/java_testing.html#test_filtering
// ./gradlew test --tests  it.unibo.testlecture.u02_unit.AppliancesTestSuite

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}