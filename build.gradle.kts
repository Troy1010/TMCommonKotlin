import com.tminus1010.tmcommonkotlin.VersionPlugin
import com.tminus1010.tmcommonkotlin.PlaygroundPlugin

// # Root Project
allprojects {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

ext {
    val versionCodeAndName = VersionPlugin.getVersionCodeAndName(project)
    set("versionCode", versionCodeAndName.first)
    set("versionName", versionCodeAndName.second)
}

subprojects {
    plugins.apply(PlaygroundPlugin::class)
    tasks.register("helloWorldTask") {
        group = "other2"
        doLast {
            val helloWorldExtension = (this@subprojects.extensions[PlaygroundPlugin.HelloWorldExtension::class.java.name] as PlaygroundPlugin.HelloWorldExtension)
            helloWorldExtension.helloWorld()
        }
    }
    if (this.getTasksByName("publishToMavenLocal", true).isNotEmpty())
        tasks.register("easyPublishLocal") {
            group = "publishing"
            dependsOn("assemble")
            finalizedBy("publishToMavenLocal")
        }
    if (this.getTasksByName("easyPublishLocal", true).isNotEmpty())
        tasks.register("easyCleanPublishLocal") {
            group = "publishing"
            dependsOn("clean")
            finalizedBy("easyPublishLocal")
            description = "Do not use in main project"
        }
}

// # easyCleanPublishLocal will stall forever if run by root project
tasks.register("easyCleanPublishLocal") { enabled = false }
tasks.register("easyCleanPublishLocalForAll") {
    group = "publishing"
    val allCleanTasks = project.getTasksByName("clean", true)
    val allAssemblePublishTasks = subprojects.mapNotNull { kotlin.runCatching { it.tasks.getByName("easyPublishLocal") }.getOrNull() }
    dependsOn(allCleanTasks, allAssemblePublishTasks)
    allAssemblePublishTasks.forEach { it.mustRunAfter(allCleanTasks) }
}