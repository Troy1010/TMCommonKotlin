package com.tminus1010.tmcommonkotlin.test

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ToDynamicTestsTest {
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
    }

    data class TestParams(
        val givenVariableX: Int,
        val expectedVariableY: Int,
    ) : TestParamsA()

    @TestFactory
    fun testWithName(): List<DynamicTest> {
        return listOf(
            WithNameTestParams(
                testName = "testName1",
                givenVariableX = 0,
                expectedVariableY = 0,
            ),
            WithNameTestParams(
                testName = "testName2",
                givenVariableX = 1,
                expectedVariableY = 1,
            ),
        ).toDynamicTests {
            // Manually check the test name
        }
    }

    data class WithNameTestParams(
        val testName: String,
        val givenVariableX: Int,
        val expectedVariableY: Int,
    ) : TestParamsA()
}