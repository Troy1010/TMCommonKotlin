package com.tminus1010.tmcommonkotlin.rx3.extensions

import com.tminus1010.tmcommonkotlin.tuple.Box
import io.reactivex.rxjava3.core.Observable


fun <T : Any> Observable<T>.box(): Observable<Box<T>> {
    return this
        .map { Box(it) }
}

fun <T : Any> Observable<T>.boxNull(): Observable<Box<T?>> {
    return this
        .map { Box(it) }
}

fun <T : Any> Observable<T>.boxStartNull(): Observable<Box<T?>> {
    return this
        .boxNull()
        .startWithItem(Box(null))
}

@Deprecated("use filterNotNullBox", ReplaceWith("this.filterNotNullBox()"))
fun <T : Any> Observable<Box<T?>>.unbox(): Observable<T> {
    return this
        .filter { it.first != null }
        .map { it.first!! }
}

fun <T : Any> Observable<Box<T?>>.filterNotNullBox(): Observable<T> {
    return this
        .filter { it.first != null }
        .map { it.first!! }
}