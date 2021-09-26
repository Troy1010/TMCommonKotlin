package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@RunWith(Parameterized::class)
class TotalTest(val givenInput: List<String>, val expectedResult: String) {
    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun params() = arrayOf(
            arrayOf(listOf("13", "9", "98"), "120"),
            arrayOf(listOf<String>(), "0")
        )
    }

    @Test
    fun test() {
        // # Given
        val list = givenInput.map { Observable.just(BigDecimal(it)) }
        // # Stimulate
        val result = list.total().timeout(2, TimeUnit.SECONDS).lastOrError().test().await()
        // # Verify
        result.assertResult(BigDecimal(expectedResult))
    }
}