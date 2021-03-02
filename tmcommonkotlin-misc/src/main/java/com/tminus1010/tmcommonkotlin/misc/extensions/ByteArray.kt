package com.tminus1010.tmcommonkotlin.misc.extensions

fun ByteArray.toLogStr() = this.map { it.toString() }.joinToString(",")