package com.tminus1010.tmcommonkotlin.rx

import com.tminus1010.tmcommonkotlin.rx.extensions.toBehaviorSubject
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Test

class ObservableExtensionsKtTest {

    @Test
    fun toBehaviorSubject() {
        // # Given
        val observable = Observable.just(1,2,3)
        // # Stimulate
        val bs = observable
            .toBehaviorSubject()
        // # Verify
        assertEquals(3, bs.value)
    }

    @Test
    fun toBehaviorSubject_Overload1() {
        // # Given
        val observable = Observable.empty<Int>()
        // # Stimulate
        val bs = observable
            .toBehaviorSubject(6)
        // # Verify
        assertEquals(6, bs.value)
    }
}