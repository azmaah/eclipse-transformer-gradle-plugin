package com.azmaah.eclipse.transformer.task;

import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Transform extends BaseTask {

  private static final String TASK_DESCRIPTION = "Transforms the jars given in the sourceDir";
  public static final String TASK_NAME = "runTransformer";

  @InputDirectory
  @SkipWhenEmpty
  public abstract DirectoryProperty getSourceDir();

  @OutputDirectory
  public abstract DirectoryProperty getDistDir();

  @InputFile
  @Optional
  public abstract RegularFileProperty getSelectionRules();

  @InputFile
  @Optional
  public abstract RegularFileProperty getRenamesRules();

  @InputFile
  @Optional
  public abstract RegularFileProperty getVersionsRules();

  @InputFile
  @Optional
  public abstract RegularFileProperty getBundlesRules();

  @InputFile
  @Optional
  public abstract RegularFileProperty getDirectRules();

  @InputFile
  @Optional
  public abstract RegularFileProperty getTextMasterRules();


  public Transform() {
    setGroup(GROUP_NAME);
    setDescription(TASK_DESCRIPTION);
    dependsOn(Setup.TASK_NAME);
  }

  @TaskAction
  void apply() {
    getProject().delete(getDistDir());
    Directory cli = cliDir.get();
    String sourceDir = getSourceDir().get().getAsFile().getPath();

    List<Object> args = new ArrayList<>();
    args.add(sourceDir);
    args.add(getDistDir().get());

    if (getSelectionRules().isPresent()) {
      args.add("--selection");
      args.add(getSelectionRules().get().getAsFile().getPath());
    }

    if (getRenamesRules().isPresent()) {
      args.add("--renames");
      args.add(getRenamesRules().get().getAsFile().getPath());
    }

    if (getVersionsRules().isPresent()) {
      args.add("--versions");
      args.add(getVersionsRules().get().getAsFile().getPath());
    }

    if (getBundlesRules().isPresent()) {
      args.add("--bundles");
      args.add(getBundlesRules().get().getAsFile().getPath());
    }

    if (getDirectRules().isPresent()) {
      args.add("--direct");
      args.add(getDirectRules().get().getAsFile().getPath());
    }

    if (getTextMasterRules().isPresent()) {
      args.add("--text");
      args.add(getTextMasterRules().get().getAsFile().getPath());
    }

    getProject().javaexec(spec -> {
      spec.setClasspath(getProject().files(cli.file(String.format(JAR_NAME + "-%s.jar", getVersion().get()))));
      spec.setArgs(args);
    });
  }
}
