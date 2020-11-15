package com.tminus1010.tmcommonkotlin

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

suspend fun <T> MutableLiveData<T>.postValue(function: suspend () -> T) {
    val result = function()
    this@postValue.postValue(result)
}

suspend fun <T> PublishSubject<T>.onNext(function: suspend () -> T) {
    val result = function()
    this@onNext.onNext(result)
}

fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(Flowable.fromObservable(observable, BackpressureStrategy.DROP))
}

fun <T> Observable<T>.toLiveData(): LiveData<T> {
    return convertRXToLiveData(this)
}

// Experimental

// This is a silly hack to make LiveData act as a life-cycle aware non-replaying observable.
// In the future, when a good life-cycle aware non-replaying observable exists, this will be unnecessary.
fun <T> LiveData<T>.onlyNew(lifecycleOwner: LifecycleOwner): LiveData<T> {
    if (this.value != null) {
        var x = Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))
        x = x.skip(1)
        return LiveDataReactiveStreams.fromPublisher(x)
    }
    return this
}