package com.tminus1010.exampleclient.extensions

import com.tminus1010.exampleclient.ThrobberSharedVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.throbberLaunch(lambda: suspend CoroutineScope.() -> Unit) {
    this.launch(block = ThrobberSharedVM.decorate(lambda))
}