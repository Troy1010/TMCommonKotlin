package com.example.tmcommonkotlin.microphone

import com.google.protobuf.ByteString

sealed class AudioEmitterResult {
    object End : AudioEmitterResult()
    class AudioChunk(val byteString: ByteString): AudioEmitterResult()
}
