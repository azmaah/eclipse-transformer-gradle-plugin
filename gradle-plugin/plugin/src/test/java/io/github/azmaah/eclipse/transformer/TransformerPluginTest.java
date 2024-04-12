package io.github.azmaah.eclipse.transformer;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerPluginTest {
  @Test void pluginRegistersTasks() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("io.github.azmaah.eclipse-transformer");

    assertNotNull(project.getTasks().findByName("setupTransformer"));
    assertNotNull(project.getTasks().findByName("runTransformer"));
  }

  @Test void pluginRegistersExtension() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("io.github.azmaah.eclipse-transformer");

    Object extension = project.getExtensions().findByName("eclipseTransformer");
    assertNotNull(extension);
    assertTrue(extension instanceof TransformerExtension);

    TransformerExtension transformerExtension = (TransformerExtension) extension;
    assertEquals("0.5.0", transformerExtension.getVersion().get());
  }
}
