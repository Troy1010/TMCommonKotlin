package com.tminus1010.tmcommonkotlin.speechtotext

import android.app.Application
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToText
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToTextResult
import com.tminus1010.tmcommonkotlin.microphone.AudioInputStream
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class OpenMicForSpeechToText(
    private val application: Application,
    private val speechToText: SpeechToText,
) {
    constructor(application: Application) : this(application, SpeechToText(application))

    operator fun invoke(closeSignal: Flow<Any>): Flow<SpeechToTextResult> {
        val audioInputStream = AudioInputStream(application)
        closeSignal.take(1).observe(GlobalScope) { audioInputStream.close() }
        return speechToText(audioInputStream, audioInputStream.sampleRate.toFloat())
    }

    // TODO: Simplify
    operator fun invoke(): Flow<SpeechToTextResult> {
        val audioInputStream = AudioInputStream(application)
        val closeSignal = MutableSharedFlow<Unit>()
        closeSignal.take(1).observe(GlobalScope) { audioInputStream.close() }
        return speechToText(audioInputStream, audioInputStream.sampleRate.toFloat())
            .onEach { if (it is SpeechToTextResult.Chunk) GlobalScope.launch(Dispatchers.Main) { closeSignal.emit(Unit) } }
    }
}