package com.tminus1010.tmcommonkotlin.misc

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


suspend fun <T> PublishSubject<T>.onNext(function: suspend () -> T) {
    val result = function()
    this@onNext.onNext(result)
}

fun <T> Observable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(Flowable.fromObservable(this, BackpressureStrategy.DROP))
}