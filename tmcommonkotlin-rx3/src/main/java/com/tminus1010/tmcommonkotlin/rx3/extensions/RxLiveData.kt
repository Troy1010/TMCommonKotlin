package com.tminus1010.tmcommonkotlin.rx3.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign

/**
 * These convenience functions make Rx work similar to LiveData
 *
 * Be careful when using with fragments.
 * Most of the time, you will want to use the fragment's viewLifecycleOwner as the lifecycleOwner.
 * Using the fragment itself as the lifecycleOwner is usually not what you want.
 */
// ## Observable
// ### Lifecycle
fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe { onNext(it) }

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe(
            { onNext(it) },
            { onError(it) })

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe(
            { onNext(it) },
            { onError(it) },
            { onComplete() })

fun <T> Observable<T>.observe(
    lifecycle: LifecycleOwner,
    observer: Observer<T>,
) {
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe(observer)
}

// ### Composite Disposable

fun <T> Observable<T>.observe(
    compositeDisposable: CompositeDisposable,
    onNext: (T) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe { onNext(it) }
        .also { compositeDisposable += it }

fun <T> Observable<T>.observe(
    compositeDisposable: CompositeDisposable,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onNext(it) }, { onError(it) })
        .also { compositeDisposable += it }

fun <T> Observable<T>.observe(
    compositeDisposable: CompositeDisposable,
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onNext(it) }, { onError(it) }, { onComplete() })
        .also { compositeDisposable += it }

//

val <T> Observable<T>.value: T?
    get() {
        var returning: T? = null
        var error: Throwable? = null
        subscribe(
            { returning = it },
            { error = it })
            .apply { dispose() }
        error?.also { throw it }
        return returning
    }

// ## Single
// ### Lifecycle
fun <T> Single<T>.observe(
    lifecycle: LifecycleOwner,
    onSuccess: (T) -> Unit
): Disposable =
    toMaybe().observe(lifecycle, onSuccess)

fun <T> Single<T>.observe(
    lifecycle: LifecycleOwner,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable =
    toMaybe().observe(lifecycle, onSuccess, onError)

fun <T> Single<T>.observe(
    lifecycle: LifecycleOwner,
    observer: MaybeObserver<T>
) {
    toMaybe().observe(lifecycle, observer)
}

// ### Composite Disposable
fun <T> Single<T>.observe(
    compositeDisposable: CompositeDisposable,
    onSuccess: (T) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe { it: T -> onSuccess(it) }
        .also { compositeDisposable += it }

fun <T> Single<T>.observe(
    compositeDisposable: CompositeDisposable,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onSuccess(it) }, { onError(it) })
        .also { compositeDisposable += it }

//
val <T> Single<T>.value: T?
    get() {
        var returning: T? = null
        var error: Throwable? = null
        subscribe(
            { returning = it },
            { error = it })
            .apply { dispose() }
        error?.also { throw it }
        return returning
    }

// ## Maybe
// ### Lifecycle
fun <T> Maybe<T>.observe(
    lifecycle: LifecycleOwner,
    onSuccess: (T) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe { it: T -> onSuccess(it) }

fun <T> Maybe<T>.observe(
    lifecycle: LifecycleOwner,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe({ onSuccess(it) }, { onError(it) })

fun <T> Maybe<T>.observe(
    lifecycle: LifecycleOwner,
    observer: MaybeObserver<T>
) {
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe(observer)
}

// ### Composite Disposable
fun <T> Maybe<T>.observe(
    compositeDisposable: CompositeDisposable,
    onSuccess: (T) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe { it: T -> onSuccess(it) }
        .also { compositeDisposable += it }

fun <T> Maybe<T>.observe(
    compositeDisposable: CompositeDisposable,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onSuccess(it) }, { onError(it) })
        .also { compositeDisposable += it }

//
val <T> Maybe<T>.value: T?
    get() {
        var returning: T? = null
        var error: Throwable? = null
        subscribe(
            { returning = it },
            { error = it })
            .apply { dispose() }
        error?.also { throw it }
        return returning
    }

// ## Completable
// ### Lifecycle
fun Completable.observe(
    lifecycle: LifecycleOwner,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe()

fun Completable.observe(
    lifecycle: LifecycleOwner,
    onComplete: () -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe { onComplete() }

fun Completable.observe(
    lifecycle: LifecycleOwner,
    onComplete: () -> Unit,
    onError: (Throwable) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .bindUntilEvent(
            lifecycle,
            event = when (lifecycle.lifecycle.currentState) {
                // confusingly, you are in STARTED state during onResume(). It only switches to RESUMED after onResume() is done.
                Lifecycle.State.STARTED -> Lifecycle.Event.ON_PAUSE
                // confusingly, you are in CREATED state during onStart(). It only switches to STARTED after onStart() is done.
                Lifecycle.State.CREATED -> Lifecycle.Event.ON_STOP
                else -> Lifecycle.Event.ON_DESTROY
            }
        )
        .subscribe(
            { onComplete() },
            { onError(it) })

// ### Composite Disposable
fun Completable.observe(
    compositeDisposable: CompositeDisposable,
): Disposable =
    subscribe()
        .also { compositeDisposable += it }

fun Completable.observe(
    compositeDisposable: CompositeDisposable,
    onComplete: () -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe { onComplete() }
        .also { compositeDisposable += it }

fun Completable.observe(
    compositeDisposable: CompositeDisposable,
    onComplete: () -> Unit,
    onError: (Throwable) -> Unit,
): Disposable =
    observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { onComplete() },
            { onError(it) })
        .also { compositeDisposable += it }
