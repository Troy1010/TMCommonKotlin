package com.tminus1010.tmcommonkotlin.coroutines.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tminus1010.tmcommonkotlin.core.logx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


inline fun <reified T> Flow<T>.doLogx(prefix: String? = null): Flow<T> {
    return onEach { it.logx(prefix) }
        .onCompletion { if (it == null) "Completed".logx(prefix) else logz("$prefix`Error:", it) }
}

inline fun <reified T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, crossinline lambda: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observe.collect { lambda(it) }
        }
    }
}

fun <T> Flow<T>.observe(coroutineScope: CoroutineScope, lambda: suspend (T) -> Unit) {
    coroutineScope.launch { this@observe.collect { lambda(it) } }
}