package com.tminus1010.tmcommonkotlin.rx.extensions

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.internal.observers.LambdaObserver

/**
 * Using observe instead of subscribe makes observables lifecycle-aware.. which means
 * their subscriptions are automatically disposed when the lifecycle owner (ie activity)
 * is destroyed.
 *
 * Be careful when using with fragments.
 * Most of the time, you will want to use the fragment's viewLifecycleOwner as the lifecycleOwner.
 * Using the fragment itself as the lifecycleOwner is usually not what you want.
 */
// *Not using default values allows for last-lambda syntax.
fun <T> Observable<T>.observe(
    lifecycleOwner: LifecycleOwner,
    onNext: (T) -> Unit
): Disposable {
    return LambdaObserver(Consumer(onNext), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer())
        .also { this.bindToLifecycle(lifecycleOwner).subscribe(it) }
}

fun <T> Observable<T>.observe(
    lifecycleOwner: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable {
    return LambdaObserver(Consumer(onNext), Consumer(onError), Functions.EMPTY_ACTION, Functions.emptyConsumer())
        .also { this.bindToLifecycle(lifecycleOwner).subscribe(it) }
}

fun <T> Observable<T>.observe(
    lifecycleOwner: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit
): Disposable {
    return LambdaObserver(Consumer(onNext), Consumer(onError), Action(onComplete), Functions.emptyConsumer())
        .also { this.bindToLifecycle(lifecycleOwner).subscribe(it) }
}

fun <T> Observable<T>.observe(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>
) {
    this.bindToLifecycle(lifecycleOwner).subscribe(observer)
}