package com.tminus1010.tmcommonkotlin.speechtotext

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.asFlow
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechStreamService
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeoutException

class SpeechToText(private val voskModelProvider: VoskModelProvider) {
    constructor(application: Application) : this(VoskModelProvider.Any(application))

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    /**
     * [sampleRate] example: 16000f
     * [inputStream] example: application.assets.open("10001-90210-01803.wav")
     */
    operator fun invoke(inputStream: InputStream, sampleRate: Float) =
        voskModelProvider.model.toObservable().asFlow()
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
                        runBlocking { results.emit(moshi.adapter(SpeechToTextResult.SoFar::class.java).fromJson(hypothesis)!!) }
                    }

                    override fun onResult(hypothesis: String) {
                        runBlocking { results.emit(moshi.adapter(SpeechToTextResult.Chunk::class.java).fromJson(hypothesis)!!) }
                    }

                    override fun onFinalResult(hypothesis: String) {
                        runBlocking { results.emit(moshi.adapter(SpeechToTextResult.Chunk::class.java).fromJson(hypothesis)!!) }
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