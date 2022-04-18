package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.rx3.extensions.disposeWith
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import junit.framework.TestCase

class DisposableKtTest : TestCase() {

    fun testDisposeWith() {
        // # Given
        val observable = Observable.just(1)
        val compositeDisposable1 = CompositeDisposable()
        val compositeDisposable2 = CompositeDisposable()
        // # Stimulate
        observable.subscribe()
            .disposeWith(compositeDisposable1, compositeDisposable2)
        // # Verify
        assertEquals(1, compositeDisposable1.size())
        assertEquals(1, compositeDisposable2.size())
    }
}