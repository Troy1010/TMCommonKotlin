package com.tminus1010.tmcommonkotlin.rx.extensions

fun <T> Sequence<T>.pairwise(): Sequence<Pair<T, T>> =
    this.zip(this.drop(1)) { a, b -> Pair(a, b) }