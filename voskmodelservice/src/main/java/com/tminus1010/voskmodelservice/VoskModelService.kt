package com.tminus1010.voskmodelservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * A dummy service, which has a context, which contains the vosk model.
 */
class VoskModelService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}