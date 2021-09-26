package com.tminus1010.tmcommonkotlin.misc.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

suspend fun <T> MutableLiveData<T>.postValue(function: suspend () -> T) {
    val result = function()
    this@postValue.postValue(result)
}

// This is a silly hack to make LiveData act as a non-replaying observable.
// In the future, when a good life-cycle aware non-replaying observable exists, this will be unnecessary.
fun <T> LiveData<T>.onlyNew(lifecycleOwner: LifecycleOwner): LiveData<T> {
    return if (this.value == null) this else {
        val flowable = Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))
            .skip(1)
        LiveDataReactiveStreams.fromPublisher(flowable)
    }
}

fun <T> LiveData<T>.toObservable(lifecycle: LifecycleOwner): Observable<T> =
    Observable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycle, this))