package com.example.tmcommonkotlin.speechtotext

sealed class SpeechToTextResult {
    data class SoFar(val x: String) : SpeechToTextResult()
    data class Chunk(val x: String) : SpeechToTextResult()
    object End : SpeechToTextResult()
}
