package com.tminus1010.tmcommonkotlin.tuple

fun <A> tuple(first: A): Box<A> {
    return Box(first)
}

fun <A, B> tuple(first: A, second: B): Pair<A, B> {
    return Pair(first, second)
}

fun <A, B, C> tuple(first: A, second: B, third: C): Triple<A, B, C> {
    return Triple(first, second, third)
}

fun <A, B, C, D> tuple(first: A, second: B, third: C, fourth: D): Quadruple<A, B, C, D> {
    return Quadruple(first, second, third, fourth)
}

fun <A, B, C, D, E> tuple(first: A, second: B, third: C, fourth: D, fifth: E): Quintuple<A, B, C, D, E> {
    return Quintuple(first, second, third, fourth, fifth)
}

fun <A, B, C, D, E, F> tuple(first: A, second: B, third: C, fourth: D, fifth: E, sixth: F): Sextuple<A, B, C, D, E, F> {
    return Sextuple(first, second, third, fourth, fifth, sixth)
}

fun <A, B, C, D, E, F, G> tuple(first: A, second: B, third: C, fourth: D, fifth: E, sixth: F, seventh: G): Septuple<A, B, C, D, E, F, G> {
    return Septuple(first, second, third, fourth, fifth, sixth, seventh)
}

fun <A, B, C, D, E, F, G, H> tuple(first: A, second: B, third: C, fourth: D, fifth: E, sixth: F, seventh: G, eighth: H): Octuple<A, B, C, D, E, F, G, H> {
    return Octuple(first, second, third, fourth, fifth, sixth, seventh, eighth)
}