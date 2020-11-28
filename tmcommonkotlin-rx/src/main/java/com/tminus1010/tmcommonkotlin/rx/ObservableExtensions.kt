package com.tminus1010.tmcommonkotlin_rx

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.internal.observers.LambdaObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Using observe instead of subscribe makes observables lifecycle-aware.. which means
 * their subscriptions are automatically disposed when the lifecycle owner (aka activity) is destroyed.
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

//

fun <T> Observable<T>.toBehaviorSubject(): BehaviorSubject<T> {
    return BehaviorSubject.create<T>()
        .also { bs -> this.subscribe { bs.onNext(it) } }
    // *Subscribing directly causes the BehaviorSubject to loose replay functionality:
//        .also { bs -> this.subscribe(bs) }
}

fun <T> Observable<T>.toBehaviorSubject(defaultValue: T): BehaviorSubject<T> {
    return this
            .startWithItem(defaultValue)
            .toBehaviorSubject()
}
