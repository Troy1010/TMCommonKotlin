package com.example.tmcommonkotlin.microphone

import android.app.Application
import com.tminus1010.tmcommonkotlin.core.generateUniqueID
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.util.concurrent.TimeUnit

class OpenMicrophoneToTempFileAndPlayback(
    private val application: Application,
    private val audioEmitter: AudioEmitter,
    private val playAudioUtil: PlayAudioUtil,
) {
    suspend operator fun invoke() {
        val tempFile = File.createTempFile(generateUniqueID(), ".file", application.cacheDir).apply { deleteOnExit() }
        audioEmitter.recordObservable(Observable.just(Unit).delay(3, TimeUnit.SECONDS))
            .doOnNext {
                if (it is AudioEmitterResult.AudioChunk)
                    tempFile.appendBytes(it.byteString.toByteArray())
            }
            .filter { it is AudioEmitterResult.End }.take(1)
            .doOnNext { logz("Record done") }
            .flatMap {
                logz("Playback start")
                playAudioUtil.playBytesObservable(tempFile, audioEmitter.partialAudioFormat)
                    .doOnNext { logz("Playback done") }
            }
            .blockingSubscribe()
    }
}