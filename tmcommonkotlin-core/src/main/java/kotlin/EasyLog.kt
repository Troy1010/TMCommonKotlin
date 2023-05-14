package kotlin
// ^ packages that start with "kotlin" are automatically imported

import android.util.Log

/**
 * This is meant as a convenience method if you don't want to bother specifying a tag.
 *
 * The message prefix is meant to help with log filtering.
 * As of 2023_05_13, Android Studio's old log filter did not allow mixing tag and message filters.
 * Perhaps with Android Studio's new log filter, this will no longer be necessary.
 */
fun logv(any: Any?, e: Throwable? = null) {
    when (any) {
        is Throwable -> Log.v("TAG", "`Error:", any)
        else -> Log.v("TAG", "`$any", e)
    }
}

inline fun <reified T> T.logvx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        when (this) {
            is Throwable -> Log.v("TAG", "`${prefixLogStr}Error:", this)
            else -> Log.v("TAG", "`${prefixLogStr}$this")
        }
    }
}

/**
 * This is meant as a convenience method if you don't want to bother specifying a tag.
 *
 * The message prefix is meant to help with log filtering.
 * As of 2023_05_13, Android Studio's old log filter did not allow mixing tag and message filters.
 * Perhaps with Android Studio's new log filter, this will no longer be necessary.
 */
fun logd(any: Any?, e: Throwable? = null) {
    when (any) {
        is Throwable -> Log.d("TAG", "`Error:", any)
        else -> Log.d("TAG", "`$any", e)
    }
}

inline fun <reified T> T.logdx(prefix: Any? = null): T {
    return this.apply {
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        when (this) {
            is Throwable -> Log.d("TAG", "`${prefixLogStr}Error:", this)
            else -> Log.d("TAG", "`${prefixLogStr}$this")
        }
    }
}

/**
 * This is meant as a convenience method to use while debugging.
 *
 * The message prefix is meant to help with log filtering.
 * As of 2023_05_13, Android Studio's old log filter did not allow mixing tag and message filters.
 * Perhaps with Android Studio's new log filter, this will no longer be necessary.
 */
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