package com.tminus1010.tmcommonkotlin.test

/**
 * Using this "A" suffix so that extending classes can be named TestParams. TODO: Is there a better way?
 */
open class TestParamsA {
    // I'm not 100% certain that this improves performance,
    private val members by lazy {
        this::class.members
    }

    // For some reason, on 2023_05_04, making this variable open and overriding it caused tests to freeze.
    val testNameByReflection: String
        get() {
            return try {
                val testNameStr = members.find { "testName" == it.name }?.call(this)?.toString()
                if (testNameStr != null) {
                    testNameStr
                } else {
                    val givenStr = members.filter { "given" in it.name }.joinToString {
                        val name = it.name.replaceFirst("given", "").replaceFirstChar { c -> c.lowercase() }
                        val value = it.call(this).toString()
                        "$name:$value"
                    }
                    val expectedStr = members.filter { "expected" in it.name }.joinToString {
                        val name = it.name.replaceFirst("expected", "").replaceFirstChar { c -> c.lowercase() }
                        val value = it.call(this).toString()
                        "$name:$value"
                    }
                    if (expectedStr.isEmpty() && givenStr.isEmpty())
                        "[No given or expected variables were detected. Please include \"given\" and/or \"expected\" in the variable names]"
                    else if (expectedStr.isEmpty())
                        "Given $givenStr"
                    else
                        "Given $givenStr __Then__ $expectedStr"
                }
            } catch (e: Throwable) {
                println("Error during testName creation:${e.stackTraceToString()}")
                "defaultTestName"
            }
        }
}