package com.tminus1010.exampleclient.ui.speechtotext

import androidx.lifecycle.ViewModel
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SpeechToTextVM @Inject constructor() : ViewModel() {
    // # View Events
    // # Events
    val openMic = MutableSharedFlow<Unit>()

    // # State
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    title = "Open Mic",
                    onClick = { runBlocking { openMic.emit(Unit) } },
                )
            )
        )
}