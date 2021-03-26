package com.tminus1010.tmcommonkotlin.rx2.extensions

import com.tminus1010.tmcommonkotlin.core.logx
import io.reactivex.Observable

inline fun <reified T> Observable<T>.doLogx(prefix: Any? = null): Observable<T> =
    this.doOnNext { it.logx(prefix) }.doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx(prefix) }
