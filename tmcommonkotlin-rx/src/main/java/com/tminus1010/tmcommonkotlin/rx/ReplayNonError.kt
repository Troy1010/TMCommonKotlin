package com.tminus1010.tmcommonkotlin.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.Semaphore

fun <T> Observable<T>.replayNonError(bufferSize: Int? = null) =
    ReplayNonErrorHelper<T>().cacheOrSource(this, bufferSize)

private class ReplayNonErrorHelper<T> {
    private val semaphore = Semaphore(1)
    private var cache: Observable<T>? = null
    fun cacheOrSource(source: Observable<T>, bufferSize: Int?): Observable<T> =
        Observable.defer {
            semaphore.acquireUninterruptibly()
            (cache ?: source
                .doOnError { cache = null }
                .run { if (bufferSize == null) replay() else replay(bufferSize) }
                .autoConnect()
                .also { cache = it })
                .also { semaphore.release() }
        }
}

fun <T> Single<T>.cacheNonError() =
    CacheNonErrorHelper<T>().cacheOrSource(this)

private class CacheNonErrorHelper<T> {
    private val semaphore = Semaphore(1)
    private var cache: Single<T>? = null
    fun cacheOrSource(source: Single<T>): Single<T> =
        Single.defer {
            semaphore.acquireUninterruptibly()
            (cache ?: source
                .doOnError { cache = null }
                .cache()
                .also { cache = it })
                .also { semaphore.release() }
        }
}