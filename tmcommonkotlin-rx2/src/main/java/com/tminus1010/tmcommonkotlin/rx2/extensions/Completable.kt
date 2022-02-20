package com.tminus1010.tmcommonkotlin.rx2.extensions

import io.reactivex.Completable

fun Completable.doLogx(prefix: Any? = null): Completable =
    doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx(prefix) }