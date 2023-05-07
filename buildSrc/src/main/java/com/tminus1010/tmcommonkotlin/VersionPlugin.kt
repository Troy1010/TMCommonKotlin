package com.tminus1010.tmcommonkotlin

import com.android.build.gradle.internal.coverage.JacocoReportTask.JacocoReportWorkerAction.Companion.logger
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

open class VersionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(VersionProvider::class.java.name, VersionProvider::class.java, project)
        project.tasks.register("printVersionCodeAndName") {
            group = "other2"
            doLast {
                val versionProvider = (project.extensions[VersionProvider::class.java.name] as VersionProvider)
                println("versionCode:${versionProvider.versionCode}\nversionName:${versionProvider.versionName}")
            }
        }
    }

    open class VersionProvider(private val project: Project) {
        private val versionCodeAndName = getVersionCodeAndName(project)
        val versionCode get() = versionCodeAndName.first
        val versionName get() = versionCodeAndName.second
    }

    companion object {
        @JvmStatic
        fun getVersionCodeAndName(project: Project): Pair<Int, String> {
            return getVersionCodeAndNameFromTag(project)
                ?: getVersionCodeAndNameFromBranch(project)
                ?: Pair(1, getVersionNameFromCommit(project))
        }

        private fun getVersionCodeAndNameFromTag(project: Project): Pair<Int, String>? {
            return try {
                val byteOut = java.io.ByteArrayOutputStream()
                project.exec {
                    commandLine = "git tag --points-at HEAD".split(" ")
                    standardOutput = byteOut
                }
                String(byteOut.toByteArray()).trim()
                    .versionCodeAndName2()
            } catch (e: Exception) {
                null
                    .also { project.logger.warn("Using default:$it for getVersionCodeAndNameFromTag because", e) }
            }
        }

        private fun getVersionNameFromCommit(project: Project): String {
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
                    .versionCodeAndName2()
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

        private fun String.versionCodeAndName2(): Pair<Int, String>? {
            val s = this
//            logger.lifecycle("start:$s")
            val groupValues = Regex("""(\d+)\.(\d+)\.(\d+).*""").find(s)?.groupValues ?: return null
//            logger.lifecycle("groups:$groupValues")
            val versionCode =
                try {
                    (groupValues[1].toInt() * 100000000 + groupValues[2].toInt() * 10000 + groupValues[3].toInt())
                        .also { logger.lifecycle("versionCode:$it") }
                } catch (e: Exception) {
                    logger.lifecycle("Unable to get versionCode", e)
                    return null
                }
            val versionName =
                try {
                    groupValues[0]
                        .also { logger.lifecycle("versionName:$it") }
                } catch (e: Exception) {
                    logger.lifecycle("Unable to get versionName", e)
                    return null
                }
            return Pair(versionCode, versionName)
        }
    }
}