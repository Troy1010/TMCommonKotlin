package com.tminus1010.tmcommonkotlin.coroutines.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


inline fun <reified T> Flow<T>.doLogx(prefix: String? = null): Flow<T> {
    return onEach { it.logx(prefix) }
        .onCompletion { if (it == null) "Completed".logx(prefix) else logz("$prefix`Error:", it) }
}

inline fun <reified T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, crossinline lambda: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            this@observe.collect { lambda(it) }
        }
    }
}

fun <T> Flow<T>.observe(coroutineScope: CoroutineScope, lambda: suspend (T) -> Unit) {
    coroutineScope.launch { this@observe.collect { lambda(it) } }
}

fun <T> Flow<T>.pairwise(): Flow<Pair<T, T>> {
    return zip(this.drop(1)) { a, b -> Pair(a, b) }
}

fun <T> Flow<T>.divertErrors(mutableSharedFlow: MutableSharedFlow<Throwable>): Flow<T> {
    return this.catch { mutableSharedFlow.emit(it) }
}