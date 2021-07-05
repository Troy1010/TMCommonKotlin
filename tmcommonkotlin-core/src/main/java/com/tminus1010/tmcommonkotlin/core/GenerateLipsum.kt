package com.tminus1010.tmcommonkotlin.core


fun generateLipsum(size: Int): List<String> {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    return (0 until size)
        .map { (0 until (4..30).random()).map { alphabet.random() }.joinToString("") }
}

fun generateLipsum(): String {
    return generateLipsum(1)[0]
}