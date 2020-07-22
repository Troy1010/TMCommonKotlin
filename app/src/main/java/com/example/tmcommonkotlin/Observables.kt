package com.example.tmcommonkotlin

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