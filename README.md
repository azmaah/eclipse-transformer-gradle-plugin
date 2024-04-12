## Eclipse Transformer Gradle Plugin

Unofficial Gradle plugin for transforming Java binaries using the [Eclipse Transformer CLI](https://projects.eclipse.org/projects/technology.transformer).

## Installation

To include this plugin, add the following to your build script (Kotlin DSL):

```kotlin
plugins {
  id("io.github.azmaah.eclipse-transformer") version "0.0.1"
}
```

## Usage

Check out the [sample project](./sample/) for an example on how to use the plugin (Kotlin DSL).

## Tasks

The plugin offers two tasks:
* `setupTransformer`: Installs the CLI in the current project.
* `runTransformer`: Transforms the jars in a given directory.

## Configuration

The plugin offers customization options through the `eclipseTransformer` extension.

##### Configuration Properties

| Property        | Type                                                                                        |
|-----------------|---------------------------------------------------------------------------------------------|
| version         | String                                                                                      |
| sourceDir       | [Directory](https://docs.gradle.org/current/javadoc/org/gradle/api/file/Directory.html)     |
| distDir         | [Directory](https://docs.gradle.org/current/javadoc/org/gradle/api/file/Directory.html)     |
| selectionRules  | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |
| renamesRules    | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |
| versionsRules   | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |
| bundlesRules    | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |
| directRules     | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |
| textMasterRules | [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html) |

* The default version for the CLI used is `0.5.0`.

##### Example

```kotlin
eclipseTransformer {
  version = "0.5.0"
  sourceDir.set(file(layout.projectDir.dir("sources")))
}
```

## Behavior

If the `java` plugin is also applied to the project and the transformed output includes jars, those jars will be automatically added to the Java sources, enhancing the build process without additional configuration.

## License

Eclipse Transformer Gradle Plugin is licensed under the MIT License.