package com.tminus1010.tmcommonkotlin.coroutines.extensions

import com.tminus1010.tmcommonkotlin.coroutines.ICoroutineScopeLambdaDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

fun CoroutineScope.launchWithDecorator(lambdaDecorator: ICoroutineScopeLambdaDecorator, lambda: suspend CoroutineScope.() -> Unit) {
    this.launch(block = lambdaDecorator.decorate(lambda))
}

fun CoroutineScope.launchWithDecorator(lambdaDecorator: ICoroutineScopeLambdaDecorator, lambda: suspend () -> Unit) {
    this.launch(block = lambdaDecorator.decorate { lambda() })
}
