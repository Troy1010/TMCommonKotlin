package com.tminus1010.tmcommonkotlin.rx.extensions

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.internal.observers.LambdaObserver

/**
 * These convenience functions make Rx work similar to LiveData
 *
 * Be careful when using with fragments.
 * Most of the time, you will want to use the fragment's viewLifecycleOwner as the lifecycleOwner.
 * Using the fragment itself as the lifecycleOwner is usually not what you want.
 */
fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread()).bindToLifecycle(lifecycle)
        .subscribe { onNext(it) }

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread()).bindToLifecycle(lifecycle)
        .subscribe({ onNext(it) }, { onError(it) })

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread()).bindToLifecycle(lifecycle)
        .subscribe({ onNext(it) }, { onError(it) }, { onComplete() })

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    observer: Observer<T>
) {
    observeOn(AndroidSchedulers.mainThread()).bindToLifecycle(lifecycle)
        .subscribe(observer)
}