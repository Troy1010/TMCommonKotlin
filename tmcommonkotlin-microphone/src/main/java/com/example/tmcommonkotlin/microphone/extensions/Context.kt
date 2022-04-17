package com.example.tmcommonkotlin.microphone.extensions

import android.content.Context
import android.media.AudioFormat
import android.media.AudioManager
import com.example.tmcommonkotlin.microphone.PartialAudioFormat


/**
 * This makes some assumptions.. like about which audio format you want, and that your microphone probably uses 16bit encoding.
 */
fun Context.getPartialAudioFormatFromMicrophone(): PartialAudioFormat {
    return PartialAudioFormat(
        encoding = AudioFormat.ENCODING_PCM_16BIT,
        sampleRate = (this.getSystemService(Context.AUDIO_SERVICE) as AudioManager).getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE).toInt()
    )
}