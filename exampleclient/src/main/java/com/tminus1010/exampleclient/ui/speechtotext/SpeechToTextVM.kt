package com.tminus1010.exampleclient.ui.speechtotext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tminus1010.exampleclient.ui.all_features.ThrobberSharedVM
import com.tminus1010.tmcommonkotlin.coroutines.extensions.doLogx
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import com.tminus1010.tmcommonkotlin.speechtotext.OpenMicForSpeechToText
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToTextResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SpeechToTextVM @Inject constructor(
    private val openMicForSpeechToText: OpenMicForSpeechToText
) : ViewModel() {
    // # View Events
    fun recordAudioForSpeechToTextGranted() {
        openMicForSpeechToText().doLogx("speechToText").let { ThrobberSharedVM.decorate(it) }.observe(viewModelScope) { speechToTextResults.emit(it) }
    }

    // # Internal
    private val speechToTextResults = MutableSharedFlow<SpeechToTextResult>()

    // # Events
    val askRecordAudioForSpeechToText = MutableSharedFlow<Unit>()

    // # State
    val speechToTextString =
        speechToTextResults.flatMapLatest {
            when (it) {
                is SpeechToTextResult.SoFar -> if (it.partial.isNotEmpty()) flowOf(it.partial) else flowOf()
                is SpeechToTextResult.Chunk -> if (it.text.isNotEmpty()) flowOf(it.text) else flowOf()
                else -> flowOf()
            }
        }
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    title = "Open Mic",
                    onClick = { runBlocking { askRecordAudioForSpeechToText.emit(Unit) } },
                )
            )
        )
}