package com.example.tmcommonkotlin.microphone

import android.annotation.SuppressLint
import android.app.Application
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.example.tmcommonkotlin.microphone.extensions.getPartialAudioFormatFromMicrophone
import java.io.IOException
import java.io.InputStream

/**
 * Recording starts on construction
 */
class AudioInputStream(
    val sampleRate: Int,
    val encoding: Int,
    audioSource: Int = MediaRecorder.AudioSource.DEFAULT,
    channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
) : InputStream() {
    constructor(application: Application) : this(application.getPartialAudioFormatFromMicrophone())
    constructor(partialAudioFormat: PartialAudioFormat) : this(partialAudioFormat.sampleRate, partialAudioFormat.encoding)

    private val bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRate, channelConfig, encoding)

    @SuppressLint("MissingPermission")
    private val audioRecord = AudioRecord(audioSource, sampleRate, channelConfig, encoding, bufferSizeInBytes)

    init {
        audioRecord.startRecording()
    }

    @Deprecated("Use read(audioData, offset, length)")
    override fun read(): Int {
        val tmp = byteArrayOf(0)
        read(tmp, 0, 1)
        return tmp[0].toInt()
    }

    override fun read(audioData: ByteArray, offset: Int, length: Int): Int {
        try {
            return audioRecord.read(audioData, offset, length)
        } catch (e: Exception) {
            throw IOException(e)
        }
    }

    override fun close() {
        audioRecord.stop()
        audioRecord.release()
        super.close()
    }
}