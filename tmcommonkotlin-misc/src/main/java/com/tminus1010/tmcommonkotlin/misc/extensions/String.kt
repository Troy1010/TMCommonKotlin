package com.tminus1010.tmcommonkotlin.misc.extensions


fun String.isAllDigits() = this.matches(Regex("^[0-9]*\$"))
fun String.noDoubleSpaces(): String {
    return if (this.contains("  ")) {
        this.replace("  ", " ").noDoubleSpaces()
    } else {
        this
    }
}