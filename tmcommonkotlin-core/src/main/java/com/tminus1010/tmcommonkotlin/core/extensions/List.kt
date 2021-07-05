package com.tminus1010.tmcommonkotlin.core.extensions

fun <T> List<List<T>>.reflectXY(): List<List<T>> {
    return this.indices.map { x ->
        this[0].indices.map { y ->
            this[y][x]
        }
    }
}