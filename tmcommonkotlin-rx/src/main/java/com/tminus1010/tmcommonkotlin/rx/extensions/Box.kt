package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.tuple.Box
import io.reactivex.rxjava3.core.Observable


fun <T> Observable<T>.box(): Observable<Box<T>> {
    return this
        .map { Box(it) }
}

fun <T> Observable<T>.boxNull(): Observable<Box<T?>> {
    return this
        .map { Box(it) }
}

fun <T> Observable<T>.boxStartNull(): Observable<Box<T?>> {
    return this
        .boxNull()
        .startWithItem(Box(null))
}

fun <T> Observable<Box<T?>>.unbox(): Observable<T> {
    return this
        .filter { it.first != null }
        .map { it.first!! }
}