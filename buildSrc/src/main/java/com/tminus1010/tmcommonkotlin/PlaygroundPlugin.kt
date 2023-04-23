package com.tminus1010.tmcommonkotlin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class PlaygroundPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(HelloWorldExtension::class.java.name, HelloWorldExtension::class.java)
    }

    open class HelloWorldExtension {
        fun helloWorld() {
            println("Hello World")
        }
    }
}