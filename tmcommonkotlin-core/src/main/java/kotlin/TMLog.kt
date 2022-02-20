package kotlin
// ^ packages that start with "kotlin" are automatically imported

import android.util.Log

fun logz(any: Any?, e: Throwable? = null) {
    try {
        when (any) {
            is Throwable -> Log.e("TMLog", "TM`Error:", any)
            else -> Log.d("TMLog", "TM`$any", e)
        }
    } catch (e2: Throwable) {
        if (e2.message?.matches(Regex(""".*android.util.Log.*""")) ?: false)
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
                is Throwable -> Log.e("TMLog", "TM`${prefixLogStr}Error:", this)
                else -> Log.d("TMLog", "TM`${prefixLogStr}$this")
            }
        } catch (e2: Throwable) {
            if (e2.message?.matches(Regex(""".*android.util.Log.*""")) ?: false)
                when (this) {
                    is Throwable -> println("TM`Error:${this.message}")
                    else -> println("TM`${prefixLogStr}$this")
                }
            else throw e2
        }
    }
}

fun logThread(prefix: Any?) =
    logz("$prefix. Thread:${Thread.currentThread().name}")