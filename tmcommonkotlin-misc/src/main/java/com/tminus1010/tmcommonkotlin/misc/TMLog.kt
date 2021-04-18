package com.tminus1010.tmcommonkotlin.misc

import com.tminus1010.tmcommonkotlin.core.logx
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

// TODO("Move to rx module")
inline fun <reified T> Observable<T>.doLogx(prefix: Any? = null, crossinline toLoggable: (T) -> Any? = { it }): Observable<T> =
    doOnNext { toLoggable(it).logx(prefix) }
        .doOnComplete { "Completed".logx(prefix) }
        .doOnError { it.logx(prefix) }

inline fun <reified T> Single<T>.doLogx(prefix: Any? = null): Single<T> =
    doOnSuccess { it.logx(prefix) }.doOnError { it.logx(prefix) }

fun Completable.doLogx(prefix: Any? = null): Completable =
    doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx("prefix") }

