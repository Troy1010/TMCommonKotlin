package com.example.tmcommonkotlin.speechtotext

import android.app.Application
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.asFlow
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeoutException

class SpeechToText(private val application: Application) {
    private val model =
        Single.create<Model> { downstream ->
            StorageService.unpack(
                application,
                "model-en-us",
                "model",
                { downstream.onSuccess(it) },
                { downstream.onError(it) },
            )
        }
            .toObservable().asFlow()

    /**
     * [sampleRate] example: 16000f
     * [inputStream] example: application.assets.open("10001-90210-01803.wav")
     */
    operator fun invoke(inputStream: InputStream, sampleRate: Float) =
        model
            .map {
                SpeechStreamService(
                    Recognizer(it, sampleRate),
                    inputStream.also { if (it.skip(44) != 44L) throw IOException("File too short") },
                    sampleRate
                )
            }
            .flatMapMerge {
                val results = MutableSharedFlow<SpeechToTextResult>()
                it.start(object : RecognitionListener {
                    override fun onPartialResult(hypothesis: String) {
                        runBlocking { results.emit(SpeechToTextResult.SoFar(hypothesis)) }
                    }

                    override fun onResult(hypothesis: String) {
                        runBlocking { results.emit(SpeechToTextResult.Chunk(hypothesis)) }
                    }

                    override fun onFinalResult(hypothesis: String) {
                        runBlocking { results.emit(SpeechToTextResult.Chunk(hypothesis)) }
                        runBlocking { results.emit(SpeechToTextResult.End) }
                    }

                    override fun onError(exception: Exception) {
                        throw exception
                    }

                    override fun onTimeout() {
                        throw TimeoutException()
                    }
                })
                results
            }
            .takeWhile { it !is SpeechToTextResult.End }
}