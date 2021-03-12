package com.tminus1010.tmcommonkotlin.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import tmextensions.*

open class TMPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            tasks.tryRegisterOrderedPair("clean", "assemble")
            tasks.register("easyCleanAssemble") {
                group = "build"
                dependsOn("clean_assemble")
            }
            if (tasks.contains("publishToMavenLocal")) {
                tasks.tryRegisterOrderedPair("assemble", "publishToMavenLocal")
                tasks.register("easyPublishLocal") {
                    group = "publishing"
                    subprojects.map { it.tasks.named("assemble_publishToMavenLocal") }
                        .also { dependsOn(it) }
                }
            }
        }
    }
}