package com.tminus1010.tmcommonkotlin.core

fun <T> tryOrNull(lambda: () -> T?): T? =
    try {
        lambda()
    } catch (e: Throwable) {
        null
    }