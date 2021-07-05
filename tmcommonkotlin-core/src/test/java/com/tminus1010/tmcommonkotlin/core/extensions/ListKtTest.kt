package com.tminus1010.tmcommonkotlin.core.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class ListKtTest {
    @Test
    fun reflectXY() {
        // # Given
        val given2d = listOf(listOf(1, 2), listOf(3, 4))
        // # When
        val result = given2d.reflectXY()
        // # Then
        assertEquals(listOf(listOf(1, 3), listOf(2, 4)), result)
    }
}