package tmextensions

import org.gradle.api.Task

fun Iterable<Task>.runInOrder() =
        pairwise().forEach { it.second.mustRunAfter(it.first) }

fun <T> Iterable<T>.pairwise(): Iterable<Pair<T, T>> =
        this.zip(this.drop(1)) { a, b -> Pair(a, b) }