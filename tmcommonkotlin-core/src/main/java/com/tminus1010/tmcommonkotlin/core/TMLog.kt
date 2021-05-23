package com.tminus1010.tmcommonkotlin.core

import android.util.Log

const val TAG = "TMLog"

fun logz(any: Any?, e: Throwable? = null) {
    when (any) {
        is Throwable -> Log.e(TAG, "TM`Error:", any)
        else -> Log.d(TAG, "TM`$any", e)
    }
}

inline fun <reified T> T.logx(prefix: Any? = null): T {
    val logWithPrefix = { any:Any? ->
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        when (any) {
            is Throwable -> Log.e(TAG, "TM`${prefixLogStr}Error:", any)
            else -> Log.d(TAG, "TM`${prefixLogStr}$any")
        }
    }
    return this.apply { logWithPrefix(this) }
}