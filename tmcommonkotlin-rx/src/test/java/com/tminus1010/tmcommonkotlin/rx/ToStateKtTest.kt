package com.tminus1010.tmcommonkotlin.rx

import com.tminus1010.tmcommonkotlin.rx.extensions.value
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class ToStateKtTest {

    @Test
    fun `When toState, value Then not null`() {
        // # When
        val observable = Observable.just(4)
            .delay(20, TimeUnit.MILLISECONDS)
            .toState(mockk(relaxed = true), mockk(relaxed = true))
        Thread.sleep(1000)
        val result = observable.value
        // # Then
        assertNotEquals(null, result)
        assertEquals(4, result)
    }
}