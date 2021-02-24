package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

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
