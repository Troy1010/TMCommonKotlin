package com.tminus1010.exampleclient.ui.imagetotext

import androidx.lifecycle.ViewModel
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import com.tminus1010.tmcommonkotlin.view.NativeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ImageToTextVM @Inject constructor() : ViewModel() {
    // # View Events
    // # Events
    val takePicture = MutableSharedFlow<Unit>()

    // # State
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    text = NativeText.Simple("Take Picture"),
                    onClick = { runBlocking { takePicture.emit(Unit) } },
                )
            )
        )
}