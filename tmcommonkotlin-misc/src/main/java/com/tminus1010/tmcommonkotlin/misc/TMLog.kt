package com.tminus1010.tmcommonkotlin.misc

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

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

inline fun <reified T> Observable<T>.doLogx(prefix: Any? = null): Observable<T> =
    this.doOnNext { it.logx(prefix) }.doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx(prefix) }

inline fun <reified T> Single<T>.doLogx(prefix: Any? = null): Single<T> =
    this.doOnSuccess { it.logx(prefix) }.doOnError { it.logx(prefix) }

fun Completable.doLogx(prefix: Any? = null): Completable =
    this.doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx("prefix") }

