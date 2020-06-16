package com.example.tmcommonkotlin.inheritables

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class TMViewModel(val disposables: CompositeDisposable = CompositeDisposable()) : ViewModel(), Disposable by disposables {

    fun finalize() {
        dispose()
    }
}