package com.example.tmcommonkotlin.speechtotext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpeechToText {
    fun openMic(): Flow<SpeechToTextResult> {
        return flow {
            emit(SpeechToTextResult.UserSaidSoFar("Hi"))
            delay(100)
            emit(SpeechToTextResult.UserSaidSoFar("Hi my"))
            delay(100)
            emit(SpeechToTextResult.UserSaidSoFar("Hi my name"))
            delay(100)
            emit(SpeechToTextResult.UserSaidSoFar("Hi my name is"))
            delay(100)
            emit(SpeechToTextResult.UserSaidSoFar("Hi my name is bob"))
            delay(100)
            emit(SpeechToTextResult.UserSaid("Hi my name is bob"))
        }
    }
}