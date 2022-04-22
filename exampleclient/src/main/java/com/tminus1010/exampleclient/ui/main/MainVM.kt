package com.tminus1010.exampleclient.ui.main

import androidx.lifecycle.ViewModel
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class MainVM : ViewModel() {
    // # Events
    val navToImageToText = MutableSharedFlow<Unit>()
    val navToOpenMicAndPlayback = MutableSharedFlow<Unit>()
    val navToSpeechToText = MutableSharedFlow<Unit>()

    // # State
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    title = "Image To Text",
                    titleTextSize = 32f,
                    onClick = { runBlocking { navToImageToText.emit(Unit) } }
                ),
                ButtonVMItem(
                    title = "Open Mic And Playback",
                    titleTextSize = 28f,
                    onClick = { runBlocking { navToOpenMicAndPlayback.emit(Unit) } }
                ),
                ButtonVMItem(
                    title = "Speech To Text",
                    titleTextSize = 28f,
                    onClick = { runBlocking { navToSpeechToText.emit(Unit) } }
                ),
            )
        )
}