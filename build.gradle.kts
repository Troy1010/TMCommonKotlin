// # Root Project
allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    if (tasks.names.contains("publishToMavenLocal"))
        tasks.register("easyPublishLocal") {
            group = "publishing"
            dependsOn("assemble")
            finalizedBy("publishToMavenLocal")
        }
    if (tasks.names.contains("easyPublishLocal"))
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