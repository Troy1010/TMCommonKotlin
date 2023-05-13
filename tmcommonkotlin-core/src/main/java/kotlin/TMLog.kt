package kotlin
// ^ packages that start with "kotlin" are automatically imported

import android.util.Log

fun logq(msg: Any?) {
    Log.d("LOGQ", "LQ`$msg")
}

fun logz(any: Any?, e: Throwable? = null) {
    when (any) {
        is Throwable -> Log.e("TMLog", "TM`Error:", any)
        else -> Log.d("TMLog", "TM`$any", e)
    }
}

inline fun <reified T> T.logx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        when (this) {
            is Throwable -> Log.e("TMLog", "TM`${prefixLogStr}Error:", this)
            else -> Log.d("TMLog", "TM`${prefixLogStr}$this")
        }
    }
}

fun logThread(prefix: Any?) =
    logz("$prefix. Thread:${Thread.currentThread().name}")