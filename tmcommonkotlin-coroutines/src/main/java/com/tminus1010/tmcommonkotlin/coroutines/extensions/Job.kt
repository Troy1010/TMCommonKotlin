package com.tminus1010.tmcommonkotlin.coroutines.extensions

import com.tminus1010.tmcommonkotlin.coroutines.IJobEvents
import kotlinx.coroutines.Job

fun Job.use(jobEvents: IJobEvents) {
    jobEvents.onStart()
    invokeOnCompletion { jobEvents.onComplete() }
}