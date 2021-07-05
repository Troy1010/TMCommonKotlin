package com.tminus1010.tmcommonkotlin.core.extensions

import org.junit.Test

import org.junit.Assert.*
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@RunWith(Enclosed::class)
class LocalDateKtTest {
    @RunWith(Parameterized::class)
    class previous(private val givenDate: LocalDate, private val expectedDate: LocalDate) {
        companion object {
            @Parameterized.Parameters(name = "Given {0} Then {1}")
            @JvmStatic
            fun params() = arrayOf<Any>(
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 7), LocalDate.of(2020, Month.FEBRUARY, 1)),
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 8), LocalDate.of(2020, Month.FEBRUARY, 1)),
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 9), LocalDate.of(2020, Month.FEBRUARY, 8)),
            )
        }

        @Test
        fun test() {
            assertEquals(expectedDate, givenDate.previous(DayOfWeek.SATURDAY))
        }
    }

    @RunWith(Parameterized::class)
    class next(private val givenDate: LocalDate, private val expectedDate: LocalDate) {
        companion object {
            @Parameterized.Parameters(name = "Given {0} Then {1}")
            @JvmStatic
            fun params() = arrayOf<Any>(
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 7), LocalDate.of(2020, Month.FEBRUARY, 8)),
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 8), LocalDate.of(2020, Month.FEBRUARY, 15)),
                arrayOf(LocalDate.of(2020, Month.FEBRUARY, 9), LocalDate.of(2020, Month.FEBRUARY, 15)),
            )
        }

        @Test
        fun test() {
            assertEquals(expectedDate, givenDate.next(DayOfWeek.SATURDAY))
        }
    }
}