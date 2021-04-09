package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T : Any> Observable<T>.pairwise(initialValue: T) = this.startWithItem(initialValue).pairwise()

fun <T : Any> Observable<T>.pairwise(): Observable<Pair<T, T>> {
    return this
        .compose {
            it
                .skip(1)
                .zipWith(it) { a, b -> Pair(b, a) }
        }
}

fun <T> Observable<T>.noEnd() = this.mergeWith(Observable.never())
fun <T> Observable<T>.endWith(item: T) = this.concatWith(Observable.just(item))
fun <T> Observable<T>.startWith(item: T) = this.startWithItem(item)

fun <IN, OUT> Observable<IN>.emitIfTimeout(timespan: Long, timeUnit: TimeUnit, item: OUT): Observable<OUT> {
    return this
        .take(1)
        .map { false }
        .timeout(timespan, timeUnit, Observable.just(true))
        .filter { it }
        .map { item }
}

fun <T : Observable<BigDecimal>> Iterable<T>.total(): Observable<BigDecimal> {
    return Observable.fromIterable(this)
        .flatMap {
            it
                .startWithItem(BigDecimal.ZERO)
                .distinctUntilChanged()
                .pairwise()
                .map { it.second - it.first }
        }
        .scan(BigDecimal.ZERO, BigDecimal::add)
}

fun <T> Observable<T>.timeoutOnce(duration: Long, timeUnit: TimeUnit, observableSource: ObservableSource<T> = Observable.error(TimeoutException())): Observable<T> =
    publish { upstream ->
        Observable.merge(
            upstream.take(1).timeout(duration, timeUnit, observableSource),
            upstream.skip(1)
        )
    }
