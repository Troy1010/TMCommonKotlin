package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.core.logx
import io.reactivex.rxjava3.core.Single

inline fun <reified T> Single<T>.doLogx(prefix: Any? = null): Single<T> =
    doOnSuccess { it.logx(prefix) }.doOnError { it.logx(prefix) }