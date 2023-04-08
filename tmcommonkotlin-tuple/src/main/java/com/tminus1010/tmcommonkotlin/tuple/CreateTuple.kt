package com.tminus1010.tmcommonkotlin.tuple

fun <A> createTuple(a: A): Box<A> {
    return Box(a)
}

fun <A, B> createTuple(a: A, b: B): Pair<A, B> {
    return Pair(a, b)
}

fun <A, B, C> createTuple(a: A, b: B, c: C): Triple<A, B, C> {
    return Triple(a, b, c)
}

fun <A, B, C, D> createTuple(a: A, b: B, c: C, d: D): Quadruple<A, B, C, D> {
    return Quadruple(a, b, c, d)
}

fun <A, B, C, D, E> createTuple(a: A, b: B, c: C, d: D, e: E): Quintuple<A, B, C, D, E> {
    return Quintuple(a, b, c, d, e)
}

fun <A, B, C, D, E, F> createTuple(a: A, b: B, c: C, d: D, e: E, f: F): Sextuple<A, B, C, D, E, F> {
    return Sextuple(a, b, c, d, e, f)
}

fun <A, B, C, D, E, F, G> createTuple(a: A, b: B, c: C, d: D, e: E, f: F, g: G): Septuple<A, B, C, D, E, F, G> {
    return Septuple(a, b, c, d, e, f, g)
}

fun <A, B, C, D, E, F, G, H> createTuple(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H): Octuple<A, B, C, D, E, F, G, H> {
    return Octuple(a, b, c, d, e, f, g, h)
}