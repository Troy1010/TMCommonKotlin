package com.tminus1010.tmcommonkotlin.core

import android.util.Log

const val TAG = "TMLog"

fun logz(any: Any?, e: Throwable? = null) {
    try {
        when (any) {
            is Throwable -> Log.e(TAG, "TM`Error:", any)
            else -> Log.d(TAG, "TM`$any", e)
        }
    } catch (e2: java.lang.RuntimeException) {
        if (e2.message?.matches(Regex(""".*in android.util.Log not mocked.*""")) ?: false)
            when (any) {
                is Throwable -> println("TM`Error:${any.message}")
                else -> println("TM`$any${e?.message?.let { " $it" } ?: ""}")
            }
        else throw e2
    }
}

inline fun <reified T> T.logx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        try {
            when (this) {
                is Throwable -> Log.e(TAG, "TM`${prefixLogStr}Error:", this)
                else -> Log.d(TAG, "TM`${prefixLogStr}$this")
            }
        } catch (e2: java.lang.RuntimeException) {
            if (e2.message?.matches(Regex(""".*in android.util.Log not mocked.*""")) ?: false)
                when (this) {
                    is Throwable -> println("TM`Error:${this.message}")
                    else -> println("TM`${prefixLogStr}$this")
                }
            else throw e2
        }
    }
}