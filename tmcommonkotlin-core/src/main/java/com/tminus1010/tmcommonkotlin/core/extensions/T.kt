package com.tminus1010.tmcommonkotlin.core.extensions

/**
 * Useful for when() blocks that you want to be exhaustive, to cause compile-time errors.
 * Example usage:
 *      when(someEnum) {
 *          SomeEnum.A -> 1
 *          SomeEnum.B -> 24
 *          SomeEnum.C -> 486
 *      }.exhaustive
 */
val <T> T.exhaustive: T
    get() = this

fun <T> T?.ifNull(lambda: () -> T?): T? {
    return this ?: lambda()
}