package com.tminus1010.tmcommonkotlin.logz

import android.util.Log

const val TAG = "TMLog"

fun logz(msg: Any?) = Log.d(TAG, "TM`$msg")

@JvmName("logz1")
fun <T> T.logx(prefix: Any? = null): T {
    return this.apply { Log.d(TAG, "TM`${prefix?.let {"$prefix`"}?:""}$this") }
}

