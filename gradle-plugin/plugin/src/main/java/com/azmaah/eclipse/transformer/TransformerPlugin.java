package com.azmaah.eclipse.transformer;

import com.azmaah.eclipse.transformer.task.BaseTask;
import com.azmaah.eclipse.transformer.task.Setup;
import com.azmaah.eclipse.transformer.task.Transform;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

/**
 * Gradle plugin that provides jar transformation from javax naming to jakarta using the Eclipse Transformer.
 *
 * @see <a href="https://github.com/eclipse/transformer">Eclipse Transformer</a>
 */
public class TransformerPlugin implements Plugin<Project> {

  private static final String EXTENSION_NAME = "eclipseTransformer";

  public void apply(Project project) {
    TransformerExtension extension = project.getExtensions().create(EXTENSION_NAME, TransformerExtension.class, project);

    project.getTasks().register(Setup.TASK_NAME, Setup.class);
    project.getTasks().register(Transform.TASK_NAME, Transform.class);

    project.getTasks().withType(BaseTask.class).configureEach(task -> {
      task.getVersion().set(extension.getVersion());
    });

    project.getTasks().withType(Transform.class).configureEach(task -> {
      task.getSourceDir().set(extension.getSourceDir());
      task.getDistDir().set(extension.getDistDir());
      task.getSelectionRules().set(extension.getSelectionRules());
      task.getRenamesRules().set(extension.getRenamesRules());
      task.getVersionsRules().set(extension.getVersionsRules());
      task.getBundlesRules().set(extension.getBundlesRules());
      task.getDirectRules().set(extension.getDirectRules());
      task.getTextMasterRules().set(extension.getTextMasterRules());
    });

    project.getPlugins().withType(JavaPlugin.class, javaPlugin -> {
      project.afterEvaluate(currentProject -> {
        currentProject.getRepositories().flatDir(spec -> spec.dir(extension.getDistDir().get()));
        currentProject.getDependencies().add(
            JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
            currentProject.fileTree(extension.getDistDir().get(), f -> f.include("*.jar"))
        );
        currentProject.getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME).dependsOn(Transform.TASK_NAME);
      });
    });
  }
}
