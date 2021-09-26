package com.tminus1010.tmcommonkotlin.core

import org.junit.Test

import org.junit.Assert.*

class GenerateLipsumKtTest {

    @Test
    fun generateLipsum0() {
        logz(generateLipsum())
        Thread.sleep(6000)
    }

    @Test
    fun generateLipsum5() {
        logz(generateLipsum(5))
        Thread.sleep(6000)
    }
}