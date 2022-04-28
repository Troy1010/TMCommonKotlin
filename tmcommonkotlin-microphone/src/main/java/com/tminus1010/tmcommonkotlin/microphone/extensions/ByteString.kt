package com.tminus1010.tmcommonkotlin.microphone.extensions

import com.google.protobuf.ByteString

fun ByteString.toLogStr(): String {
    return this.toByteArray().toLogStr()
}