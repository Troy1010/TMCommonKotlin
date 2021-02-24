package com.tminus1010.tmcommonkotlin.rx.extensions

fun ByteArray.toLogStr() = this.map { it.toString() }.joinToString(",")