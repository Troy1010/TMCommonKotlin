package com.tminus1010.tmcommonkotlin.rx2.extensions

import com.tminus1010.tmcommonkotlin.core.logx
import io.reactivex.Single

inline fun <reified T> Single<T>.doLogx(prefix: Any? = null): Single<T> =
    doOnSuccess { it.logx(prefix) }.doOnError { it.logx(prefix) }