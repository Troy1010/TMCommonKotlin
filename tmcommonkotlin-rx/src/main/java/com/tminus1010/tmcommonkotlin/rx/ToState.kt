package com.tminus1010.tmcommonkotlin.rx

import com.tminus1010.tmcommonkotlin.rx.extensions.toSingle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.Semaphore

/**
 * non-lazily caches 1.
 * disposable is given to compositeDisposable.
 * If an error occurs, it is emitted by errorSubject.
 */
fun <T> Observable<T>.toState(compositeDisposable: CompositeDisposable, errorSubject: Subject<Throwable>) =
    ToStateHelper<T>().cacheOrSource(this, compositeDisposable, errorSubject)

private class ToStateHelper<T> {
    private val semaphore = Semaphore(1)
    private var cache: Observable<T>? = null
    fun cacheOrSource(source: Observable<T>, compositeDisposable: CompositeDisposable, errorSubject: Subject<Throwable>): Observable<T> {
        return Observable.defer {
            semaphore.acquireUninterruptibly()
            (cache ?: source
                .onErrorResumeNext { cache = null; errorSubject.onNext(it); Observable.empty() }
                .replay(1)
                .also { compositeDisposable.add(it.connect()) }
                .also { cache = it })
                .also { semaphore.release() }
        }
    }
}

fun <T> Single<T>.toState(compositeDisposable: CompositeDisposable, errorSubject: Subject<Throwable>) =
    ToStateHelper<T>().cacheOrSource(this.toObservable(), compositeDisposable, errorSubject).toSingle()