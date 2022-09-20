package kotlin
// ^ packages that start with "kotlin" are automatically imported

import android.util.Log
import com.tminus1010.tmcommonkotlin.core.isNonInstrumentationTest

fun logz(any: Any?, e: Throwable? = null) {
    if (isNonInstrumentationTest)
        when (any) {
            is Throwable -> println("TM`Error:${any.message}")
            else -> println("TM`$any${e?.stackTraceToString() ?: ""}")
        }
    else
        when (any) {
            is Throwable -> Log.e("TMLog", "TM`Error:", any)
            else -> Log.d("TMLog", "TM`$any", e)
        }
}

inline fun <reified T> T.logx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        if (isNonInstrumentationTest)
            when (this) {
                is Throwable -> println("TM`Error:${this.message}")
                else -> println("TM`${prefixLogStr}$this")
            }
        else
            when (this) {
                is Throwable -> Log.e("TMLog", "TM`${prefixLogStr}Error:", this)
                else -> Log.d("TMLog", "TM`${prefixLogStr}$this")
            }
    }
}

fun logThread(prefix: Any?) =
    logz("$prefix. Thread:${Thread.currentThread().name}")