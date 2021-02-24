package com.tminus1010.tmcommonkotlin.rx.extensions

import junit.framework.TestCase

class MutableMapKtTest : TestCase() {

    fun testRemoveIf() {
        // Given
        val mutableMap = mutableMapOf(3 to 6, 4 to 2)
        // Stimulate
        mutableMap.removeIf { it.key < 4 }
        // Verify
        assertEquals(1, mutableMap.entries.size)
        assertEquals(4 to 2, mutableMap.entries.first().toPair())
    }
}