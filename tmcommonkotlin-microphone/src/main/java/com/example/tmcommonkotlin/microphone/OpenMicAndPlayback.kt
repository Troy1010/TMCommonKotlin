package com.example.tmcommonkotlin.microphone

import android.app.Application
import com.tminus1010.tmcommonkotlin.core.generateUniqueID
import io.reactivex.rxjava3.core.Observable
import java.io.File

class OpenMicAndPlayback(
    private val application: Application,
    private val playAudioFile: PlayAudioFile = PlayAudioFile(),
) {
    suspend operator fun invoke(closeMicSignal: Observable<Unit>) {
        val tempFile = File.createTempFile(generateUniqueID(), ".file", application.cacheDir).apply { deleteOnExit() }
        logz("Recording start")
        val audioInputStream = AudioInputStream(application)
        closeMicSignal.take(1).subscribe { audioInputStream.close() }
        tempFile.appendBytes(audioInputStream.readBytes())
        logz("Playback start")
        playAudioFile(tempFile, PartialAudioFormat(audioInputStream.encoding, audioInputStream.sampleRate))
        logz("Playback done")
    }
}