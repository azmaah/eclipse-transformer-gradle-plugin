package io.github.azmaah.eclipse.transformer;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

/**
 * Configures the {@link TransformerPlugin} tasks.
 * <p>
 * The extension can be used (in Kotlin DSL) as the following:
 * <pre>
 * eclipseTransformer {
 *   version.set("0.5.0")
 *   sourceDir.set(file(layout.buildDirectory.dir("libs")))
 *   distDir.set(file(layout.buildDirectory.dir("output")))
 * }
 * </pre>
 */
public class TransformerExtension {

  private static final String DEFAULT_CLI_VERSION = "0.5.0";

  /**
   * The Eclipse Transformer CLI's version
   */
  private final Property<String> version;

  /**
   * The directory containing the jars to transform
   */
  private final DirectoryProperty sourceDir;

  /**
   * The directory where to store the transformed jars
   */
  private final DirectoryProperty distDir;

  /**
   * The file containing the properties for selections rules
   */
  private final RegularFileProperty selectionRules;

  /**
   * The file containing the properties for renames rules
   */
  private final RegularFileProperty renamesRules;

  /**
   * The file containing the properties for versions rules
   */
  private final RegularFileProperty versionsRules;

  /**
   * The file containing the properties for bundles rules
   */
  private final RegularFileProperty bundlesRules;

  /**
   * The file containing the properties for direct rules
   */
  private final RegularFileProperty directRules;

  /**
   * The file containing the properties for text master rules
   */
  private final RegularFileProperty textMasterRules;

  public TransformerExtension(Project project) {
    ObjectFactory objects = project.getObjects();
    version = objects.property(String.class).convention(DEFAULT_CLI_VERSION);
    distDir = objects.directoryProperty().convention(project.getLayout().getBuildDirectory().dir("transform-output"));
    sourceDir = objects.directoryProperty();
    selectionRules = objects.fileProperty();
    renamesRules = objects.fileProperty();
    versionsRules = objects.fileProperty();
    bundlesRules = objects.fileProperty();
    directRules = objects.fileProperty();
    textMasterRules = objects.fileProperty();
  }

  public Property<String> getVersion() {
    return version;
  }

  public DirectoryProperty getSourceDir() {
    return sourceDir;
  }

  public DirectoryProperty getDistDir() {
    return distDir;
  }

  public RegularFileProperty getSelectionRules() {
    return selectionRules;
  }

  public RegularFileProperty getRenamesRules() {
    return renamesRules;
  }

  public RegularFileProperty getVersionsRules() {
    return versionsRules;
  }

  public RegularFileProperty getBundlesRules() {
    return bundlesRules;
  }

  public RegularFileProperty getDirectRules() {
    return directRules;
  }

  public RegularFileProperty getTextMasterRules() {
    return textMasterRules;
  }
}
