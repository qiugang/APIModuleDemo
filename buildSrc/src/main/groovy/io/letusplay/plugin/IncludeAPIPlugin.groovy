package io.letusplay.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class IncludeAPIPlugin implements Plugin<Project> {

  List<String> apiSdks = new ArrayList<>()

  @Override
  void apply(Project project) {

    Configuration configurations = project.configurations.create("includeApi")
    configurations.canBeResolved = true
    configurations.canBeConsumed = true
    configurations.visible = true

    project.afterEvaluate {
      configurations.allDependencies.all {
        dep ->
          String targetName = "${dep.name}-api"
          if (!apiSdks.contains(targetName)) {
            project.dependencies.add("api", project.getRootProject().project(targetName))
            apiSdks.add(targetName)
          }
      }
    }
  }
}