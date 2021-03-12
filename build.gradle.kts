// # Root Project

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    tasks.register("easyPublishLocal") {
        group = "publishing"
        dependsOn("assemble")
        finalizedBy("publishToMavenLocal")
    }
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
    val allAssemblePublishTasks = subprojects.map { it.tasks.getByName("easyPublishLocal") }
    dependsOn(allCleanTasks, allAssemblePublishTasks)
    allAssemblePublishTasks.forEach { it.mustRunAfter(allCleanTasks) }
}