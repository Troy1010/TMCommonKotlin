package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class ObservableKtTest {
    @Test
    fun total() {
        // # Given
        val list = listOf(
            Observable.just(BigDecimal(13)),
            Observable.just(BigDecimal(9)),
            Observable.just(BigDecimal(98))
        )
        // # Stimulate
        val result = list.total().timeout(2, TimeUnit.SECONDS).test().await()
        // # Verify
        result.assertResult(BigDecimal("13"), BigDecimal("22"), BigDecimal("120"))
    }
}