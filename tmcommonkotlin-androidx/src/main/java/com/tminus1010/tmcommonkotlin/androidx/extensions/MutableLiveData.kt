package com.tminus1010.tmcommonkotlin.androidx.extensions

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.onNext(x: T) {
    this.value = x
}