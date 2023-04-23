package com.tminus1010.tmcommonkotlin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

open class FindVersionCodeAndNamePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(FindVersionCodeAndNameExtension::class.java.name, FindVersionCodeAndNameExtension::class.java, project)
        project.tasks.register("printVersionName") {
            group = "other2"
            doLast {
                val findVersionCodeAndNameExtension = (project.extensions[FindVersionCodeAndNameExtension::class.java.name] as FindVersionCodeAndNameExtension)
                println(findVersionCodeAndNameExtension.versionName)
            }
        }
    }

    open class FindVersionCodeAndNameExtension(private val project: Project) {
        // TODO: Should this have caching?
        val versionName
            get() = getVersionCodeAndNameFromBranch(project)?.second ?: getSnapshotVersionNameFromCommit(project)

        // TODO: Should this have caching?
        val versionCode
            get() = getVersionCodeAndNameFromBranch(project)?.first ?: 0 // TODO: Is this a good default?
    }

    companion object {
        private fun getSnapshotVersionNameFromCommit(project: Project): String {
            return try {
                val byteOut = java.io.ByteArrayOutputStream()
                project.exec {
                    commandLine = "git rev-parse --short HEAD".split(" ")
                    standardOutput = byteOut
                }
                "SNAPSHOT-${String(byteOut.toByteArray()).trim()}"
            } catch (e: Exception) {
                "SNAPSHOT-DEFAULT"
                    .also { project.logger.warn("Using default:$it for getSnapshotVersionNameFromCommit because", e) }
            }
        }

        /**
         * Utility function to retrieve the name of the current git branch.
         * Will not work if build tool detaches head after checkout, which some do!
         */
        private fun getVersionCodeAndNameFromBranch(project: Project): Pair<Int, String>? {
            return try {
                val byteOut = java.io.ByteArrayOutputStream()
                project.exec {
                    commandLine = "git rev-parse --abbrev-ref HEAD".split(" ")
                    standardOutput = byteOut
                }
                String(byteOut.toByteArray()).trim()
                    .also {
                        if (it == "HEAD")
                            project.logger.warn("Unable to determine current branch: Project is checked out with detached head!")
                    }
                    .versionCodeAndName()
            } catch (e: Exception) {
                project.logger.warn("Unable to getVersionCodeAndName", e)
                null
            }
        }

        private fun String.versionCodeAndName(): Pair<Int, String>? {
            val s = this
//    logger.lifecycle("start:$s")
            val groupValues = Regex("""v([\d]+)_(.+)""").find(s)?.groupValues ?: return null
//    logger.lifecycle("groups:$groupValues")
            val versionCode =
                try {
                    groupValues[1].toInt()
//                .also { logger.lifecycle("versionCode:$it") }
                } catch (e: Exception) {
//            logger.lifecycle("Unable to get versionCode", e)
                    return null
                }
            val versionName =
                try {
                    groupValues[2]
//                .also { logger.lifecycle("versionName:$it") }
                } catch (e: Exception) {
//            logger.lifecycle("Unable to get versionName", e)
                    return null
                }
            return Pair(versionCode, versionName)
        }
    }
}