package com.tminus1010.tmcommonkotlin.core

val isNonInstrumentationTest by lazy {
    Thread.currentThread().stackTrace.any { it.className.startsWith("org.junit.") }
}