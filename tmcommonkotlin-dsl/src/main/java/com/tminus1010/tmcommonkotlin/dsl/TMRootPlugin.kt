package com.tminus1010.tmcommonkotlin.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import tmextensions.*

open class TMRootPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            subprojects.forEach { it.tasks.tryRegisterOrderedPair("assemble", "publishToMavenLocal") }
            tasks.register("clean_assemble_publish") {
                group = "publishing"
                val allCleanTasks = project.getTasksByName("clean", true)
                val allAssemblePublishTasks = subprojects.map { it.tasks.getByName("assemble_publishToMavenLocal") }
                dependsOn(allCleanTasks, allAssemblePublishTasks)
                allAssemblePublishTasks.forEach { it.mustRunAfter(allCleanTasks) }
            }
            tasks.register("clean_assemble") {
                group = "build"
                val allCleanTasks = project.getTasksByName("clean", true)
                val allAssembleTasks = subprojects.map { it.tasks.getByName("assemble") }
                dependsOn(allCleanTasks, allAssembleTasks)
                allAssembleTasks.forEach { it.mustRunAfter(allCleanTasks) }
            }
        }
    }
}