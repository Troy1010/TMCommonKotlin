package com.tminus1010.exampleclient

import com.tminus1010.tmcommonkotlin.coroutines.IJobEvents
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

object ThrobberSharedVM : IJobEvents {
    // # Input
    fun <T> decorate(flow: Flow<T>) = flow.onStart { asyncTaskStarted.emit(Unit) }.onCompletion { asyncTaskEnded.emit(Unit) }
    fun decorate(completable: Completable) = completable.doOnSubscribe { runBlocking { asyncTaskStarted.emit(Unit) } }.doOnTerminate { runBlocking { asyncTaskEnded.emit(Unit) } }

    override fun onStart() {
        runBlocking { asyncTaskStarted.emit(Unit) }
    }

    override fun onComplete() {
        runBlocking { asyncTaskEnded.emit(Unit) }
    }

    suspend fun asyncTaskStarted() {
        asyncTaskStarted.emit(Unit)
    }

    suspend fun asyncTaskEnded() {
        asyncTaskEnded.emit(Unit)
    }

    // # Internal
    private val asyncTaskStarted = MutableSharedFlow<Unit>()
    private val asyncTaskEnded = MutableSharedFlow<Unit>()

    // # State
    val isVisible =
        merge(asyncTaskStarted.map { 1 }, asyncTaskEnded.map { -1 })
            .scan(0) { acc, v -> acc + v }
            .map { it != 0 }
            .distinctUntilChanged()
            .shareIn(GlobalScope, SharingStarted.Eagerly, 1)
}