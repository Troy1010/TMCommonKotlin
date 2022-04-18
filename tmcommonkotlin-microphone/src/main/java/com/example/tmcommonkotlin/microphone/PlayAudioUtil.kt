package com.example.tmcommonkotlin.microphone

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.io.File
import java.io.FileInputStream


class PlayAudioUtil {
    private val onCompletionSubject = PublishSubject.create<MediaPlayer>()

    fun playMP3Observable(file: File): Observable<Unit> {
        return Observable.just(Unit)
            .map {
                MediaPlayer()
                    .also { mediaPlayer ->
                        mediaPlayer.setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                                .build()
                        )
                        mediaPlayer.setDataSource(FileInputStream(file).fd)
                        mediaPlayer.setOnCompletionListener { onCompletionSubject.onNext(mediaPlayer) }
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    }
            }
            .flatMap { x -> onCompletionSubject.map { Pair(x, it) } }
            .filter { (a, b) -> a === b }
            .map { (a, _) -> a.release() }
    }
}