package com.tminus1010.tmcommonkotlin.core

/**
 * This is not 100% reliable.
 */
val isNonInstrumentationTest by lazy {
    Thread.currentThread().stackTrace.any { it.className.startsWith("org.junit.") }
}