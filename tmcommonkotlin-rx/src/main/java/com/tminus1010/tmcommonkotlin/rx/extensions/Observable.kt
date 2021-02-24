package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.tuple.Box
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

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

fun <T> Observable<Box<T?>>.unbox(): Observable<T> {
    return this
        .filter { it.first != null }
        .map { it.first!! }
}

fun <T : Any> Observable<T>.pairwise(initialValue: T) = this.startWithItem(initialValue).pairwise()

fun <T : Any> Observable<T>.pairwise(): Observable<Pair<T, T>> {
    return this
        .compose {
            it
                .skip(1)
                .zipWith(it) { a, b -> Pair(b, a) }
        }
}
