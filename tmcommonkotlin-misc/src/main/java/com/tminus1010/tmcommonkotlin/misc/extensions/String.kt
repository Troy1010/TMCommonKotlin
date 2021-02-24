package com.tminus1010.tmcommonkotlin.misc.extensions


fun String.isAllDigits() = this.matches(Regex("^[0-9]*\$"))
fun String.noDoubleSpaces(): String {
    return if (this.contains("  ")) {
        this.replace("  ", " ").noDoubleSpaces()
    } else {
        this
    }
}

fun String.toByteArray(): ByteArray {
    require(this.isAllDigits()) { "String must only contain digits" }
    return this
        .map { it.toString().toByte() }
        .fold(ByteArray(0)) { acc, bytes -> acc + bytes }
}