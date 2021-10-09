package com.tminus1010.tmcommonkotlin.core.extensions

fun <T> List<List<T>>.reflectXY(): List<List<T>> {
    return try {
        this[0].indices.map { y ->
            this.indices.map { x ->
                this[x][y]
            }
        }
    } catch (e: IndexOutOfBoundsException) {
        throw Exception("Probably was not a 2d grid", e)
    }
}