package com.tminus1010.tmcommonkotlin.test

import org.junit.jupiter.api.DynamicTest

/**
 * A convenience function to avoid boilerplate.
 */
fun <T : TestParamsA> List<T>.toDynamicTests(lambda: (T) -> Unit): List<DynamicTest> {
    return this.map { testParams ->
        DynamicTest.dynamicTest(testParams.testNameByReflection) {
            lambda(testParams)
        }
    }
}

/**
 * A convenience function if you only want to see one test.
 *
 * This should only be used while debugging.
 */
fun List<DynamicTest>.only(i: Int): List<DynamicTest> {
    return listOfNotNull(getOrNull(i))
}