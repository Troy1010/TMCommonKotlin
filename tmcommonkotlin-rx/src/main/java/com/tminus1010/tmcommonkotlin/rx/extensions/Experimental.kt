package com.tminus1010.tmcommonkotlin.rx.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject


fun <T> Observable<T>.toBehaviorSubject(): BehaviorSubject<T> {
    return BehaviorSubject.create<T>()
        .also { bs ->
            // Direct subscription (ie: this.subscribe(behaviorSubject)) is ignored by behaviorSubject.value.
            this.subscribe { bs.onNext(it) }
        }
}

fun <T> Observable<T>.toBehaviorSubject(defaultValue: T) =
    this.startWithItem(defaultValue).toBehaviorSubject()

fun <T> Observable<T>.isCold(): Boolean {
    return this
        .toBehaviorSubject()
        .value != null
}