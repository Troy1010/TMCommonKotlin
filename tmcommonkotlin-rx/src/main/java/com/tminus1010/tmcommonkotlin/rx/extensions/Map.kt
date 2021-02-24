package com.tminus1010.tmcommonkotlin.rx.extensions


fun <K, V> Map<K, V>.toHashMap() = HashMap(this)

fun <K, V, K2, V2> Map<K, V>.associate(action: (Map.Entry<K, V>) -> Pair<K2, V2>) =
    this.entries.associate(action)