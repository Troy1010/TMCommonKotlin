package com.tminus1010.tmcommonkotlin.core.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class reflectXY(private val given2D: List<List<Int>>, private val expected2D: List<List<Int>>) {
    companion object {
        @Parameterized.Parameters(name = "Given {0} Then {1}")
        @JvmStatic
        fun params() = arrayOf<Any>(
            arrayOf(listOf(listOf(1, 2), listOf(3, 4)), listOf(listOf(1, 3), listOf(2, 4))),
            arrayOf(listOf(listOf(1, 2, 3), listOf(4, 5, 6)), listOf(listOf(1, 4), listOf(2, 5), listOf(3, 6)))
        )
    }

    @Test
    fun test() {
        assertEquals(expected2D, given2D.reflectXY())
    }
}