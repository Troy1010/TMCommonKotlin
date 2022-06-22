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
                    text = "Image To Text",
                    textSize = 32f,
                    onClick = { runBlocking { navToImageToText.emit(Unit) } }
                ),
                ButtonVMItem(
                    text = "Open Mic And Playback",
                    textSize = 28f,
                    onClick = { runBlocking { navToOpenMicAndPlayback.emit(Unit) } }
                ),
                ButtonVMItem(
                    text = "Speech To Text",
                    textSize = 32f,
                    onClick = { runBlocking { navToSpeechToText.emit(Unit) } }
                ),
            )
        )
}