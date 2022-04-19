package com.tminus1010.tmcommonkotlin.rx3.extensions

import io.reactivex.rxjava3.core.Single

inline fun <reified T : Any> Single<T>.doLogx(prefix: Any? = null): Single<T> =
    doOnSuccess { it.logx(prefix) }.doOnError { it.logx(prefix) }