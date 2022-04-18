package com.example.tmcommonkotlin.microphone.extensions

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack

fun AudioFormat.getAudioRecordMinBufferSize(): Int {
    return AudioRecord.getMinBufferSize(sampleRate, channelMask, encoding)
}

fun AudioFormat.getAudioTrackMinBufferSize(): Int {
    return AudioTrack.getMinBufferSize(sampleRate, channelMask, encoding)
}