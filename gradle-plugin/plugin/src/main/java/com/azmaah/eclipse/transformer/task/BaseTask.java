package com.azmaah.eclipse.transformer.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.Directory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

public abstract class BaseTask extends DefaultTask {

  protected final String GROUP_NAME = "transformer";
  protected final String JAR_NAME = "org.eclipse.transformer.cli";
  protected final Provider<Directory> cliDir = getProject().getLayout().getBuildDirectory().dir("eclipse-transformer");

  @Input
  public abstract Property<String> getVersion();
}
