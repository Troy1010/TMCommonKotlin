package com.tminus1010.tmcommonkotlin.core

val isJUnitTest by lazy {
    Thread.currentThread().stackTrace.any { it.className.startsWith("org.junit.") }
}