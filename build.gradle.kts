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

tasks.register("bumpVersion") {
    group = "publishing"
    doLast {
        println("Hi")
//        val version_ = version as String
        val version_ = "1.0.1"
        val minor=version_.substring(version_.lastIndexOf('.')+1)
        val m=minor.toInt()+1
        val major= version_.substring(0,version_.length-1)
        val s=buildFile.readText().replaceFirst("version='$version_'", "version='$major$m'")
        buildFile.writeText(s)
    }
}