plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

version = "0.0.1"
group = "com.azmaah.eclipse.transformer"

dependencies {
    testImplementation(libs.junit)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
        val functionalTest by registering(JvmTestSuite::class)
    }
}

configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

gradlePlugin {
    website.set("https://github.com/azmaah/eclipse-transformer-gradle-plugin")
    vcsUrl.set("https://github.com/azmaah/eclipse-transformer-gradle-plugin")
    plugins {
        val eclipseTransformerGradlePlugin by creating {
            id = "com.azmaah.eclipse.transformer"
            implementationClass = "com.azmaah.eclipse.transformer.TransformerPlugin"
            displayName = "Gradle Eclipse Transformer plugin"
            description = "A Gradle plugin to transform Java binaries using the Eclipse Transformer CLI."
            tags.set(listOf("transform", "eclipse", "java"))
        }
    }
    testSourceSets(sourceSets.getByName("functionalTest"))
}

tasks.named<Task>("check") {
    dependsOn("functionalTest")
}
