package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@Deprecated("")
fun Completable.launch(scheduler: Scheduler = Schedulers.io()) =
    this.subscribeOn(scheduler).subscribe()

fun Completable.doLogx(prefix: Any? = null): Completable =
    doOnComplete { "Completed".logx(prefix) }.doOnError { it.logx(prefix) }