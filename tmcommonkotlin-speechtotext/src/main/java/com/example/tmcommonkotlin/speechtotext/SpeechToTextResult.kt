package com.example.tmcommonkotlin.speechtotext

sealed class SpeechToTextResult {
    class UserSaidSoFar(val x: String) : SpeechToTextResult()
    class UserSaid(val x: String) : SpeechToTextResult()
}
