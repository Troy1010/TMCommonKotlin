package com.tminus1010.tmcommonkotlin.coroutines.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

inline fun <reified T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, crossinline lambda: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            this@observe.collect { lambda(it) }
        }
    }
}

fun <T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, lifecycleState: Lifecycle.State = Lifecycle.State.STARTED) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            this@observe.collect()
        }
    }
}

fun <T> Flow<T>.observe(coroutineScope: CoroutineScope, lambda: suspend (T) -> Unit): Job {
    return coroutineScope.launch { this@observe.collect { lambda(it) } }
}

fun <T> Flow<T>.observe(coroutineScope: CoroutineScope): Job {
    return coroutineScope.launch { this@observe.collect { } }
}

fun <T> Flow<T>.pairwise(): Flow<Pair<T, T>> {
    return zip(this.drop(1)) { a, b -> Pair(a, b) }
}

fun <T> Flow<T>.pairwiseStartNull(): Flow<Pair<T?, T>> {
    return map { it as T? }.onStart { emit(null) }.zip(this) { a, b -> Pair(a, b) }
}

fun <T> Flow<T>.divertErrors(mutableSharedFlow: MutableSharedFlow<Throwable>): Flow<T> {
    return this.catch { mutableSharedFlow.emit(it) }
}

fun <T> Flow<T>.takeUntil(signal: Flow<Any>): Flow<T> = flow {
    try {
        coroutineScope {
            launch {
                signal.take(1).collect()
                this@coroutineScope.cancel()
            }
            collect {
                emit(it)
            }
        }
    } catch (e: CancellationException) {
        //ignore
    }
}

inline fun <reified T> Flow<T>.doLogx(prefix: Any? = null): Flow<T> =
    onEach { it.logx(prefix) }.onCompletion { (if (it is CancellationException) "Cancelled".logx(prefix) else it ?: "Completed").logx(prefix) }