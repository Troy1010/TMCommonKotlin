package com.example.tmcommonkotlin.microphone.extensions

import com.google.protobuf.ByteString

fun ByteString.toLogStr(): String {
    return this.toByteArray().toLogStr()
}