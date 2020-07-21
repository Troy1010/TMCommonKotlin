package com.example.tmcommonkotlin.extensions

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> MutableLiveData<T>.postResult(viewModelScope: CoroutineScope, function: suspend () -> T) {
    viewModelScope.launch {
        val result = function()
        this@postResult.postValue(result)
    }
}

suspend fun <T> MutableLiveData<T>.postResult(function: suspend () -> T) {
    val result = function()
    this@postResult.postValue(result)
}

fun <T> PublishSubject<T>.postResult(viewModelScope: CoroutineScope, function: suspend () -> T) {
    viewModelScope.launch {
        val result = function()
        this@postResult.onNext(result)
    }
}

suspend fun <T> PublishSubject<T>.postResult(function: suspend () -> T) {
    val result = function()
    this@postResult.onNext(result)
}