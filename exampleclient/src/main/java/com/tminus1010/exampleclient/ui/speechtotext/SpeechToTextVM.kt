package com.tminus1010.exampleclient.ui.speechtotext

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tminus1010.exampleclient.ui.all_features.ThrobberSharedVM
import com.tminus1010.tmcommonkotlin.coroutines.extensions.doLogx
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import com.tminus1010.tmcommonkotlin.speechtotext.OpenMicForSpeechToText
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToText
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToTextResult
import com.tminus1010.tmcommonkotlin.view.NativeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SpeechToTextVM @Inject constructor(
    private val openMicForSpeechToText: OpenMicForSpeechToText,
    private val speechToText: SpeechToText,
    private val application: Application,
) : ViewModel() {
    // # View Events
    fun recordAudioForSpeechToTextGranted() {
        openMicForSpeechToText().doLogx("speechToText").let { ThrobberSharedVM.decorate(it) }.observe(viewModelScope) { speechToTextResults.emit(it) }
    }

    // # User Intents
    fun userUsePrerecordedFile() {
        speechToText(application.assets.open("10001-90210-01803.wav"), 16000f).doLogx("speechToText").let { ThrobberSharedVM.decorate(it) }.observe(viewModelScope) { speechToTextResults.emit(it) }
    }

    // # Internal
    private val speechToTextResults = MutableSharedFlow<SpeechToTextResult>()

    // # Events
    val askRecordAudioForSpeechToText = MutableSharedFlow<Unit>()

    // # State
    val speechToTextString =
        speechToTextResults.flatMapLatest {
            when (it) {
                is SpeechToTextResult.SoFar -> flowOf(it.partial)
                is SpeechToTextResult.Chunk -> flowOf(it.text)
                else -> flowOf()
            }
        }
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    text = NativeText.Simple("Use Prerecorded file"),
                    onClick = ::userUsePrerecordedFile,
                ),
                ButtonVMItem(
                    text = NativeText.Simple("Open Mic"),
                    onClick = { runBlocking { askRecordAudioForSpeechToText.emit(Unit) } },
                ),
            )
        )
}