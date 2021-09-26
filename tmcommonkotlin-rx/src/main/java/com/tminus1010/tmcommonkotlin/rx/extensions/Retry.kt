package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

/**
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun <T> Single<T>.retryWithDelay(delay: Long, timeUnit: TimeUnit, maxRetries: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Single<T> {
    return this.retryWhen { upstreamExceptions ->
        var retryCount = 0
        upstreamExceptions.flatMap { throwable ->
            if (predicate(throwable) && (maxRetries == null || retryCount++ < maxRetries))
                Flowable.timer(delay, timeUnit)
            else
                Flowable.error(throwable)
        }
    }
}

/**
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun Completable.retryWithDelay(delay: Long, timeUnit: TimeUnit, maxRetries: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Completable {
    return this.retryWhen { upstreamExceptions ->
        var retryCount = 0
        upstreamExceptions.flatMap { throwable ->
            if (predicate(throwable) && (maxRetries == null || retryCount++ < maxRetries))
                Flowable.timer(delay, timeUnit)
            else
                Flowable.error<Unit>(throwable)
        }
    }
}

/**
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun <T> Observable<T>.retryWithDelay(delay: Long, timeUnit: TimeUnit, maxRetries: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Observable<T> {
    return this.retryWhen { upstreamExceptions ->
        var retryCount = 0
        upstreamExceptions.flatMap { throwable ->
            if (predicate(throwable) && (maxRetries == null || retryCount++ < maxRetries))
                Observable.timer(delay, timeUnit)
            else
                Observable.error(throwable)
        }
    }
}

/**
 * Retry with delay1 until maxRetries1, then with delay2 until maxRetries2
 *
 * @param maxRetries1 How many times do you want to retry with delay1?
 * @param maxRetries2 How many times do you want to retry with delay2?
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun <T> Single<T>.retryWith2Delays(delay1: Long, timeUnit1: TimeUnit, delay2: Long, timeUnit2: TimeUnit, maxRetries1: Int? = null, maxRetries2: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Single<T> {
    return this.retryWhen { upstreamExceptions ->
        var retryCount1 = 0
        var retryCount2 = 0
        upstreamExceptions.flatMap { throwable ->
            val predicateResult = predicate(throwable)
            if (predicateResult && (maxRetries1 == null || retryCount1++ < maxRetries1))
                Flowable.timer(delay1, timeUnit1)
            else if (predicateResult && (maxRetries2 == null || retryCount2++ < maxRetries2))
                Flowable.timer(delay2, timeUnit2)
            else
                Flowable.error(throwable)
        }
    }
}

/**
 * Retry with delay1 until maxRetries1, then with delay2 until maxRetries2
 *
 * @param maxRetries1 How many times do you want to retry with delay1?
 * @param maxRetries2 How many times do you want to retry with delay2?
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun Completable.retryWith2Delays(delay1: Long, timeUnit1: TimeUnit, delay2: Long, timeUnit2: TimeUnit, maxRetries1: Int? = null, maxRetries2: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Completable {
    return this.retryWhen { upstreamExceptions ->
        var retryCount1 = 0
        var retryCount2 = 0
        upstreamExceptions.flatMap { throwable ->
            val predicateResult = predicate(throwable)
            if (predicateResult && (maxRetries1 == null || retryCount1++ < maxRetries1))
                Flowable.timer(delay1, timeUnit1)
            else if (predicateResult && (maxRetries2 == null || retryCount2++ < maxRetries2))
                Flowable.timer(delay2, timeUnit2)
            else
                Flowable.error(throwable)
        }
    }
}

/**
 * Retry with delay1 until maxRetries1, then with delay2 until maxRetries2
 *
 * @param maxRetries1 How many times do you want to retry with delay1?
 * @param maxRetries2 How many times do you want to retry with delay2?
 * @param predicate Exposes the throwable so that you can return true to retry, or false to not retry.
 */
fun <T> Observable<T>.retryWith2Delays(delay1: Long, timeUnit1: TimeUnit, delay2: Long, timeUnit2: TimeUnit, maxRetries1: Int? = null, maxRetries2: Int? = null, predicate: ((Throwable) -> Boolean) = { true }): Observable<T> {
    return this.retryWhen { upstreamExceptions ->
        var retryCount1 = 0
        var retryCount2 = 0
        upstreamExceptions.flatMap { throwable ->
            val predicateResult = predicate(throwable)
            if (predicateResult && (maxRetries1 == null || retryCount1++ < maxRetries1))
                Observable.timer(delay1, timeUnit1)
            else if (predicateResult && (maxRetries2 == null || retryCount2++ < maxRetries2))
                Observable.timer(delay2, timeUnit2)
            else
                Observable.error(throwable)
        }
    }
}