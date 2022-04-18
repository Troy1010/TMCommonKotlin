package com.tminus1010.tmcommonkotlin.rx3.extensions

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.disposeWith(vararg compositeDisposables: CompositeDisposable) {
    compositeDisposables.forEach { it.add(this) }
}