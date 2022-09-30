package com.tminus1010.tmcommonkotlin.coroutines.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun <R> (suspend () -> R).observe(coroutineScope: CoroutineScope): Job {
    return coroutineScope.launch { this@observe.invoke() }
}