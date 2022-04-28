package com.tminus1010.tmcommonkotlin.speechtotext

sealed class SpeechToTextResult {
    data class SoFar(val partial: String) : SpeechToTextResult()
    data class Chunk(val text: String) : SpeechToTextResult()
    object End : SpeechToTextResult()
}
