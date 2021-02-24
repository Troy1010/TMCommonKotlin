package com.tminus1010.tmcommonkotlin.rx.extensions

import android.media.AudioRecord
import android.os.Build
import androidx.annotation.RequiresApi
import com.tminus1010.tmcommonkotlin.rx.UnableToBuildAudioRecord
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Creating AudioRecord is finicky.
 *
 * For example, if you try to build an AudioRecord very close to when another AudioRecord closes, it can fail.
 *      ^the retry operators in this method should solve this.
 * However, it can also fail with the exact same exception if RECORD_AUDIO was not granted at runtime.
 *
 * In future versions of AudioRecord, this method will probably be unnecessary.
 */
@RequiresApi(Build.VERSION_CODES.M)
fun AudioRecord.Builder.buildObservable(): Single<AudioRecord> {
    return Single.create<AudioRecord> { downstream ->
        try {
            downstream.onSuccess(this.build())
        } catch (t: Throwable) {
            when (t) {
                is java.lang.UnsupportedOperationException -> downstream.onError(
                    UnableToBuildAudioRecord(t)
                )
                else -> downstream.onError(t)
            }
        }
    }
        .subscribeOn(Schedulers.newThread()) // This might not be necessary.
        .retryWith2Delays(125, TimeUnit.MILLISECONDS, 2, TimeUnit.SECONDS, maxRetries1 = 2, maxRetries2 = 2)
}