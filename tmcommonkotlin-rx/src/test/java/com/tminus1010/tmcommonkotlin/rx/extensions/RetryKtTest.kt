package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.concurrent.TimeUnit

@RunWith(Enclosed::class)
class RetryKtTest : TestCase() {
    @RunWith(Parameterized::class)
    class Single_retryWith2Delays(
        val expectErrors: Boolean,
        val givenMaxRetries1: Int,
        val givenMaxRetries2: Int,
        val givenSuccessIndex: Int
    ) {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun params() = listOf<Array<*>>(
                arrayOf<Any>(false, 10, 1, 6),
                arrayOf<Any>(false, 10, 1, 10),
                arrayOf<Any>(false, 4, 2, 6),
                arrayOf<Any>(true, 1, 1, 6),
                arrayOf<Any>(false, 1, 1, 1),
                arrayOf<Any>(true, 1, 0, 2),
            )
        }

        @Test
        fun test() {
            // # Given
            var counter = 0
            Single.create<Unit> { downstream ->
                when (counter++) {
                    givenSuccessIndex -> downstream.onSuccess(Unit).also { println("Success") }
                    else -> downstream.onError(Exception("$counter")).also { println("$counter") }
                }
            }
                // # Stimulate
                .retryWith2Delays(
                    100,
                    TimeUnit.MILLISECONDS,
                    3,
                    TimeUnit.SECONDS,
                    givenMaxRetries1,
                    givenMaxRetries2
                )
                // # Verify
                .test()
                .apply {
                    await(8, TimeUnit.SECONDS)
                    if (expectErrors) assertError { true } else assertNoErrors()
                }
        }
    }

    @RunWith(Parameterized::class)
    class Single_retryWithDelay(
        val expectErrors: Boolean,
        val givenMaxRetries: Int,
        val givenSuccessIndex: Int,
        val givenRepeatCount: Int
    ) {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun params() = listOf<Array<*>>(
                arrayOf<Any>(false, 10, 6, 1),
                arrayOf<Any>(false, 10, 10, 1),
                arrayOf<Any>(true, 4, 6, 2),
                arrayOf<Any>(false, 6, 1, 6),
                arrayOf<Any>(true, 1, 6, 1),
                arrayOf<Any>(false, 1, 1, 6),
                arrayOf<Any>(true, 1, 2, 6),
            )
        }

        @Test
        fun test() {
            // # Given
            var counter = 0
            val observable = Single.create<Unit> { downstream ->
                when (counter++) {
                    givenSuccessIndex -> downstream.onSuccess(Unit).also { println("Success") }
                    else -> downstream.onError(Exception("$counter")).also { println("$counter") }
                }
            }
            // # Stimulate & Verify
            repeat(givenRepeatCount) {
                observable
                    .retryWithDelay(100, TimeUnit.MILLISECONDS, givenMaxRetries)
                    .test()
                    .apply {
                        await(8, TimeUnit.SECONDS)
                        if (expectErrors) assertError { true } else assertNoErrors()
                        counter = 0
                    }
            }
        }
    }
}