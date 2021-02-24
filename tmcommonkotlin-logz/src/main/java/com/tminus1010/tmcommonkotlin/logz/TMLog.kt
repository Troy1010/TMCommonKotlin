package com.tminus1010.tmcommonkotlin.logz

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

const val TAG = "TMLog"

@JvmName("logz2")
fun logz(any: Any?) {
    when (any) {
        is Throwable -> Log.e(TAG, "TM`Error:", any)
        else -> Log.d(TAG, "TM`$any")
    }
}

@JvmName("logz1")
inline fun <reified T> T.logx(prefix: Any? = null): T {
    val logWithPrefix = { any:Any? ->
        val prefixLogStr = prefix?.let { "$prefix`" } ?: ""
        when (any) {
            is Throwable -> Log.e(TAG, "TM`${prefixLogStr}Error:", any)
            else -> Log.d(TAG, "TM`${prefixLogStr}$any")
        }
    }
    return when(T::class.java) {
        Observable::class.java -> (this as Observable<*>).doOnNext { logWithPrefix(it) }.doOnComplete { logWithPrefix("Completed") }.doOnError { logWithPrefix(it) } as T
        Single::class.java -> (this as Single<*>).doOnSuccess { logWithPrefix(it) }.doOnError { logWithPrefix(it) } as T
        Completable::class.java -> (this as Completable).doOnComplete { logWithPrefix("Completed") }.doOnError { logWithPrefix(it) } as T
        else -> this.apply { logWithPrefix(this) }
    }
}

