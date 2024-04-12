package io.github.azmaah.eclipse.transformer.task;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.Directory;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.UUID;

public abstract class Setup extends BaseTask {

  private static final String TASK_DESCRIPTION = "Installs the Eclipse Transformer CLI";
  public static final String TASK_NAME = "setupTransformer";

  public Setup() {
    setGroup(GROUP_NAME);
    setDescription(TASK_DESCRIPTION);
  }

  @OutputDirectory
  public Provider<Directory> getOutputDir() {
    return cliDir;
  }

  @TaskAction
  public void apply() {
    getProject().delete(cliDir);
    String configurationName = String.format("cli-%s", UUID.randomUUID());
    String version = getVersion().get();

    Configuration cliConfiguration = getProject().getConfigurations().create(configurationName);
    cliConfiguration.setTransitive(false);

    getProject()
        .getDependencies()
        .add(cliConfiguration.getName(), String.format("org.eclipse.transformer:%s:%s:distribution", JAR_NAME, version));

    File installation = cliConfiguration.getSingleFile();

    getProject().copy(spec -> {
      spec.from(getProject().zipTree(installation));
      spec.into(cliDir);
    });

    getProject().getConfigurations().remove(cliConfiguration);
  }
}
