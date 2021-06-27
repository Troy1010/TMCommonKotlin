package com.tminus1010.tmcommonkotlin.core

import android.util.Log

inline fun <reified T> T.logx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        try {
            when (this) {
                is Throwable -> Log.e("TMLog", "TM`${prefixLogStr}Error:", this)
                else -> Log.d("TMLog", "TM`${prefixLogStr}$this")
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