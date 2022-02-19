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
            tasks.tryRegisterOrderedPair("clean", "uninstallDebug")
            tasks.tryRegisterOrderedPair("clean", "uninstallAll")
            tasks.tryRegisterOrderedPair("clean_uninstallDebug", "assembleDebug")
            if (tasks.contains("publishToMavenLocal"))
                tasks.tryRegisterOrderedPair("assemble", "publishToMavenLocal")
                    .configure { group = "publishing" }
            tasks.register("assembleDebug2") {
                group = "build"
                dependsOn(tasks.named("assembleDebug"))
            }
            tasks.register("rebuildReinstall") {
                description = "For debug and androidTest apks"
                group = "other2"
                dependsOn("clean", "installDebug", "installDebugAndroidTest")
            }
            tasks.register("rebuild") {
                description = "For debug and androidTest apks"
                group = "other2"
                dependsOn("clean", "assembleDebug", "assembleDebugAndroidTest")
            }
        }
    }
}