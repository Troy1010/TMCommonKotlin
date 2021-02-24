package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable

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
