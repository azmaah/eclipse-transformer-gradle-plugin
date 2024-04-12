import io.github.azmaah.eclipse.transformer.task.Transform

plugins {
    id("base")
    id("java")
    id("io.github.azmaah.eclipse-transformer")
}

val libsDir = file(layout.buildDirectory.dir("libs"))

eclipseTransformer {
    sourceDir.set(libsDir)
}

val jakartaTransformation by configurations.creating

repositories {
    mavenCentral()
}

dependencies {
    //jakartaTransformation("<YOUR_DEPENDENCY_DECLARATION")
}

val copyDependencies = tasks.register<Copy>("copyDependencies") {
    from(jakartaTransformation)
    into(libsDir)
}

tasks.getByName(Transform.TASK_NAME).dependsOn(copyDependencies)