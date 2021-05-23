package com.tminus1010.tmcommonkotlin.rx2.extensions

import com.tminus1010.tmcommonkotlin.core.logx
import io.reactivex.Completable

fun Completable.doLogx(prefix: Any? = null): Completable =
    this.doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx("prefix") }