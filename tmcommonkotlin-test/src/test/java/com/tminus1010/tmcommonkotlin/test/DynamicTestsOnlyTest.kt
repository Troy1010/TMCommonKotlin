package com.tminus1010.tmcommonkotlin.test

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DynamicTestsOnlyTest {
    @TestFactory
    fun test(): List<DynamicTest> {
        return listOf(
            TestParams(
                givenVariableX = 1,
                expectedVariableY = 1,
            ),
            TestParams(
                givenVariableX = 2,
                expectedVariableY = 2,
            ),
        ).toDynamicTests {
            // Manually check the test name
        }
            .only(0)
    }

    data class TestParams(
        val givenVariableX: Int,
        val expectedVariableY: Int,
    ) : TestParamsA()
}