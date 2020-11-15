package com.tminus1010.tmcommonkotlin.misc

import kotlinx.coroutines.*

object Coroutines {
    fun <T:Any> ioThenMain(work: suspend (()->T?), callback: ((T?)->Unit)): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }
    }
}