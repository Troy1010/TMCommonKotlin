package com.tminus1010.exampleclient

import android.app.Application
import androidx.annotation.Keep
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@Keep
open class BaseApp : Application() {
    override fun onCreate() {
        logz("!*!*! START")
        super.onCreate()

        // # Configure Rx
        RxJavaPlugins.setErrorHandler { throw it.cause ?: it }
    }
}