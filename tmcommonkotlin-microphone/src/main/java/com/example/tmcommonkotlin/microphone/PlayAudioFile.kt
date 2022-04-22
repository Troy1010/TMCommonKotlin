package com.tminus1010.tmcommonkotlin.microphone

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.tminus1010.tmcommonkotlin.microphone.extensions.getAudioTrackMinBufferSize
import java.io.File
import java.io.FileInputStream

/**
 * Plays a file which is just audio bytes, not mp3.
 */
class PlayAudioFile {
    operator fun invoke(file: File, partialAudioFormat: PartialAudioFormat) {
        val audioFormat =
            AudioFormat.Builder()
                .setEncoding(partialAudioFormat.encoding)
                .setSampleRate(partialAudioFormat.sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()
        val audioAttributes =
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        val audioTrack =
            AudioTrack.Builder()
                .setAudioFormat(audioFormat)
                .setAudioAttributes(audioAttributes)
                .setBufferSizeInBytes(audioFormat.getAudioTrackMinBufferSize())
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
        audioTrack.play() // MODE_STREAM means that play() does not play, it just opens the stream.

        val inputStream = FileInputStream(file)
        val audioData = ByteArray(audioFormat.getAudioTrackMinBufferSize())
        var dataSize = 0
        while (dataSize != -1) {
            audioTrack.write(audioData, 0, dataSize)
            dataSize = inputStream.read(audioData)
        }
        audioTrack.stop()
        audioTrack.release()
    }
}