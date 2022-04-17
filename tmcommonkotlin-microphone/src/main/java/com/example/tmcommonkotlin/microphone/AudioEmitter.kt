package com.example.tmcommonkotlin.microphone

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tmcommonkotlin.microphone.extensions.getAudioRecordMinBufferSize
import com.example.tmcommonkotlin.microphone.extensions.toLogStr
import com.google.protobuf.ByteString
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Feeds audio from the mic into a subscriber function.
 * It is from: https://github.com/GoogleCloudPlatform/android-docs-samples
 */
@RequiresApi(Build.VERSION_CODES.M) // AudioRecord requires SDK 23+
class AudioEmitter(val partialAudioFormat: PartialAudioFormat) {
    private var mAudioRecorder: AudioRecord? = null
    private var mAudioExecutor: ScheduledExecutorService? = null
    private lateinit var mBuffer: ByteArray

    fun recordObservable(stopObservable: Observable<Unit>): Observable<AudioEmitterResult> {
        val audioChunkPublisher = PublishSubject.create<ByteString>()
        return Observable.merge(
            Observable.just(Unit)
                .doOnNext { start { audioChunkPublisher.onNext(it) } }
                .flatMap { stopObservable.take(1) }
                .doOnNext { stop() }
                .map { AudioEmitterResult.End },
            audioChunkPublisher
                .map { AudioEmitterResult.AudioChunk(it) }
        )
    }

    /** Start streaming  */
    @SuppressLint("MissingPermission")
    private fun start(subscriber: (ByteString) -> Unit) {
        mAudioExecutor = Executors.newSingleThreadScheduledExecutor()
        val audioFormat = AudioFormat.Builder()
            .setEncoding(partialAudioFormat.encoding)
            .setSampleRate(partialAudioFormat.sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
            .build()

        // create and configure recorder
        // Note: ensure settings are match the speech recognition config
        mAudioRecorder = AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(audioFormat)
            .build()
        mBuffer = ByteArray(2 * audioFormat.getAudioRecordMinBufferSize())

        // start!
        mAudioRecorder!!.startRecording()

        // stream bytes as they become available in chunks equal to the buffer size
        mAudioExecutor!!.scheduleAtFixedRate({
            // read audio data
            val read = mAudioRecorder!!.read(mBuffer, 0, mBuffer.size, AudioRecord.READ_BLOCKING)
//            logz("AudioChunk as buffer:${mBuffer.toLogStr()}")

            // send next chunk
            if (read > 0) {
                subscriber(ByteString.copyFrom(mBuffer, 0, read))
            }
        }, 0, 10, TimeUnit.MILLISECONDS)
    }

    /** Stop Streaming  */
    private fun stop() {
        // stop events
        mAudioExecutor?.shutdown()
        mAudioExecutor = null

        // stop recording
        mAudioRecorder?.stop()
        mAudioRecorder?.release()
        mAudioRecorder = null
    }
}