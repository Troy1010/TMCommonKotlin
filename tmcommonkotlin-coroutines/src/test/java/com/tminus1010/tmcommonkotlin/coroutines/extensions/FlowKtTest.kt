package com.tminus1010.tmcommonkotlin.coroutines.extensions

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FlowKtTest {

    @Test
    fun pairwise() = runBlocking {
        // # Given
        val givenFlow =
            flow {
                emit(1)
                emit(2)
                emit(3)
            }
        // # When
        val result = givenFlow.pairwise().toList()
        // # Then
        assertEquals(listOf(Pair(1, 2), Pair(2, 3)), result)
    }
}