package com.example.tmcommonkotlin

val <T> T.exhaustive: T
    get() = this



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


fun String.hasDigit(): Boolean {
    for (ch in this) {
        if (ch.isDigit()) {
            return true
        }
    }
    return false
}