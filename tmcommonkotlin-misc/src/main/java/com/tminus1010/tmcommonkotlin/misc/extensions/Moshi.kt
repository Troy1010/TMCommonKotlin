package com.tminus1010.tmcommonkotlin.misc.extensions

import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.toJson(x: T): String =
    this.adapter(T::class.java).toJson(x)

inline fun <reified T : Any> Moshi.fromJson(s: String): T {
    if (s == "null") error("string was \"null\". If you are expecting nulls - even \"null\" strings - then make the input string nullable.")
    return this.adapter(T::class.java).fromJson(s) ?: error("s:$s produced null result")
}

@JvmName("fromJsonNullable")
inline fun <reified T> Moshi.fromJson(s: String?): T? {
    if (s == null) return null
    return this.adapter(T::class.java).fromJson(s)
}