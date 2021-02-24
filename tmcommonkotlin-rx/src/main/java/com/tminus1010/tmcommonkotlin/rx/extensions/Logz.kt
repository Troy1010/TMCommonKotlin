package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.logz.logx
import io.reactivex.rxjava3.core.Observable


fun <T> Observable<T>.logz(msg:Any?) =
    this.doOnNext { it.logx(msg) }