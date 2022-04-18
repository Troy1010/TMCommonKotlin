package com.tminus1010.tmcommonkotlin.rx3.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.lang.ref.WeakReference


fun <T> Observable<T>.toBehaviorSubject(): BehaviorSubject<T> {
    return BehaviorSubject.create<T>()
        .also { behaviorSubject ->
            // Direct subscription (ie: this.subscribe(behaviorSubject)) is ignored by behaviorSubject.value.
            val behaviorSubjectWeakRef = WeakReference<BehaviorSubject<T>>(behaviorSubject)
            this
                .takeUntil { behaviorSubjectWeakRef.get() == null }
                .subscribe(
                    { behaviorSubjectWeakRef.get()?.onNext(it) },
                    { behaviorSubjectWeakRef.get()?.onError(it) },
                    { behaviorSubjectWeakRef.get()?.onComplete() })
        }
}

fun <T> Observable<T>.toBehaviorSubject(defaultValue: T) =
    this.startWithItem(defaultValue).toBehaviorSubject()

fun <T> Observable<T>.isCold(): Boolean {
    return this.value != null
}