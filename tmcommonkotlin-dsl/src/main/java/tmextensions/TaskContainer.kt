package tmextensions

import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider

fun TaskContainer.getByPath(vararg taskNames: String) =
    taskNames.map { this.getByPath(it) }

/**
 * Returns a newly registered task, or a task that was already registered for the combined name.
 *
 * Attempts to create a new task, which runs the first task and then the second.
 * Unfortunately, when the first task has sub-tasks, it doesn't always work.
 */
fun TaskContainer.tryRegisterOrderedPair(
    nameOfFirstTask: String,
    nameOfSecondTask: String
): TaskProvider<Task> {
    return try {
        register("${nameOfFirstTask}_${nameOfSecondTask}") {
            dependsOn(named(nameOfFirstTask))
            finalizedBy(named(nameOfSecondTask))
            description = "$nameOfFirstTask & $nameOfSecondTask"
            group = "ordered task pair"
        }
    } catch (e: Throwable) {
        named("${nameOfFirstTask}_${nameOfSecondTask}")
    }
}

fun TaskContainer.contains(taskName: String): Boolean {
    return try {
        getByName(taskName)
        true
    } catch (e: Throwable) {
        false
    }
}