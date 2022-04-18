package com.tminus1010.tmcommonkotlin.misc

val fnName
    get() = Throwable().stackTrace[1].methodName

fun getFnName(level: Int = 1) {
    Throwable().stackTrace[level].methodName
}