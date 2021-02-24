package com.tminus1010.tmcommonkotlin.misc.extensions


fun String.isAllDigits(): Boolean {
    for (ch in this) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}
fun String.noDoubleSpaces():String {
    return if (this.contains("  ")) {
        this.replace("  ", " ").noDoubleSpaces()
    } else {
        this
    }
}