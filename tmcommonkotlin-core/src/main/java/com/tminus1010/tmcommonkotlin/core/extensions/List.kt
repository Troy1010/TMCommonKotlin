package com.tminus1010.tmcommonkotlin.core.extensions

fun <T> List<List<T>>.reflectXY(): List<List<T>> {
    return this[0].indices.map { x ->
        this.indices.map { y ->
            this[y][x]
        }
    }
}