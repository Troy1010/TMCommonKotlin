package com.tminus1010.tmcommonkotlin.misc.extensions

fun <K, V> MutableMap<K, V>.removeIf(function: (Map.Entry<K, V>) -> Boolean): MutableMap<K, V> {
    return this.apply {
        toMap().asSequence()
            .filter(function)
            .forEach { this.remove(it.key) }
    }
}