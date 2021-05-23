package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@Deprecated("use RxKotlin")
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}