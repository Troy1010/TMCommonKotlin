package com.tminus1010.tmcommonkotlin.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import tmextensions.*

open class TMPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            tasks.tryRegisterOrderedPair("clean", "assemble")
                .configure { group = "build" }
            tasks.tryRegisterOrderedPair("clean_assemble", "uninstallDebug")
                .configure { group = "install" }
            tasks.tryRegisterOrderedPair("clean_assemble_uninstallDebug", "installDebug")
                .configure { group = "install" }
            if (tasks.contains("publishToMavenLocal"))
                tasks.tryRegisterOrderedPair("assemble", "publishToMavenLocal")
                    .configure { group = "publishing" }
        }
    }
}