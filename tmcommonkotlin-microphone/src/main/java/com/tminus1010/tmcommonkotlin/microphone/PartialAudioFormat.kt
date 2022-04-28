package com.tminus1010.tmcommonkotlin.microphone

import android.media.AudioFormat

/**
 * [encoding] example: [AudioFormat.ENCODING_PCM_16BIT]
 * [sampleRate] example: 16000
 */
data class PartialAudioFormat(
    val encoding: Int,
    val sampleRate: Int,
)