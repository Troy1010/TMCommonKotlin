package com.tminus1010.tmcommonkotlin.core

import java.util.*

fun generateUniqueID(): String {
    return UUID.randomUUID().toString().replace("-", "").uppercase(Locale.getDefault())
}