package com.tminus1010.tmcommonkotlin.coroutines

import kotlinx.coroutines.CoroutineScope

interface ICoroutineScopeLambdaDecorator {
    fun decorate(lambda: suspend CoroutineScope.() -> Unit): suspend CoroutineScope.() -> Unit
}
