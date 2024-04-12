package io.github.azmaah.eclipse.transformer;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerPluginFunctionalTest {

  @TempDir
  File projectDir;

  private File getBuildFile() {
    return new File(projectDir, "build.gradle");
  }

  private File getSettingsFile() {
    return new File(projectDir, "settings.gradle");
  }

  @Test void canSetupTransformer() throws IOException {
    writeString(getSettingsFile(), "");
    writeString(getBuildFile(),"""
      plugins {
        id "io.github.azmaah.eclipse-transformer"
      }
      
      repositories {
        mavenCentral()
      }
    """);

    GradleRunner runner = GradleRunner.create();
    runner.forwardOutput();
    runner.withPluginClasspath();
    runner.withArguments("setupTransformer");
    runner.withProjectDir(projectDir);
    BuildResult result = runner.build();
    BuildTask task = result.task(":setupTransformer");
    assertNotNull(task);
    assertSame(task.getOutcome(), TaskOutcome.SUCCESS);

    Path path = Paths.get(projectDir.getPath(), "build", "eclipse-transformer");
    assertTrue(Files.exists(path));
  }

  @Test void canRunTransformer() throws IOException {
    String jarName = "example.jar";

    // Create folder in testing project to use in extension
    Path path = Paths.get(projectDir.getPath(), "sources");
    Files.createDirectory(path);

    // Copy jar to testing project
    Path destinationJar = Paths.get(path + "/" + jarName);
    Files.copy(Paths.get("src", "functionalTest", "resources", jarName), destinationJar);

    writeString(getSettingsFile(), "");
    writeString(getBuildFile(),"""
      plugins {
        id "io.github.azmaah.eclipse-transformer"
      }
      
      repositories {
        mavenCentral()
      }
      
      eclipseTransformer {
        sourceDir.set(file(layout.projectDir.dir("sources")))
      }
    """);


    GradleRunner runner = GradleRunner.create();
    runner.forwardOutput();
    runner.withPluginClasspath();
    runner.withArguments("runTransformer");
    runner.withProjectDir(projectDir);
    BuildResult result = runner.build();
    BuildTask task = result.task(":runTransformer");
    assertNotNull(task);
    assertSame(task.getOutcome(), TaskOutcome.SUCCESS);
    assertTrue(result.getOutput().contains("Transformer Return Code [ 0 ] [ Success ]"));
  }

  private void writeString(File file, String string) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      writer.write(string);
    }
  }
}
