package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

fun Completable.launch(scheduler: Scheduler = Schedulers.io()) =
    this.subscribeOn(scheduler).subscribe()