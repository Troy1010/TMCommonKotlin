package com.tminus1010.tmcommonkotlin.core


fun generateLipsum(size: Int): List<String> {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    val returning = ArrayList<String>()
    for (i in 0 until size) {
        var s = ""
        val sizeOfWord = (4..30).random()
        for (j in 0 until sizeOfWord) {
            s += alphabet.random()
        }
        returning.add(s)
    }
    return returning.toList()
}

fun generateLipsum(): String {
    return generateLipsum(1)[0]
}