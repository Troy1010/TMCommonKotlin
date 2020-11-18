package com.tminus1010.tmcommonkotlin.misc

import kotlinx.coroutines.*

object Coroutines {
    fun <T:Any> ioThenMain(ioAction: suspend (()->T?), mainThreadAction: ((T?)->Unit)): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt ioAction()
            }.await()
            mainThreadAction(data)
        }
    }
}