package com.tminus1010.tmcommonkotlin.rx

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class CheckNetwork {
    class CheckNetworkException(override val cause: Throwable) : Exception(
        """Could not ping google.com"""
    )

    val completable: Completable
        get() {
            val urlConnection = URL("https://clients3.google.com/generate_204")
                .openConnection() as HttpURLConnection
            return Completable.fromCallable {
                urlConnection.apply {
                    setRequestProperty("User-Agent", "Android")
                    setRequestProperty("Connection", "close")
                    connect()
                    if (responseCode !in 200..299) error("Did not get valid responseCode:$responseCode")
                }
            }.subscribeOn(Schedulers.io())
                .timeout(5, TimeUnit.SECONDS)
                .onErrorResumeNext { Completable.error(CheckNetworkException(it)) }
                .doOnTerminate { urlConnection.disconnect() }
        }
}