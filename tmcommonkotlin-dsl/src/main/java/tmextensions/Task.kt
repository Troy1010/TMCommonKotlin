package tmextensions

import org.gradle.api.Task

fun Task.dependsOnInOrder(tasks: Iterable<Task>) {
    this.dependsOn(tasks)
    tasks.runInOrder()
}