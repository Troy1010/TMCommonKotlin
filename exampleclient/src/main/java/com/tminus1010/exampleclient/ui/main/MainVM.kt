package com.tminus1010.exampleclient.ui.main

import androidx.lifecycle.ViewModel
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class MainVM : ViewModel() {
    // # Events
    val navToImageToText = MutableSharedFlow<Unit>()

    // # State
    val buttons =
        flowOf(
            listOf(
                ButtonVMItem(
                    title = "Image To Text",
                    titleTextSize = 32f,
                    onClick = { runBlocking { navToImageToText.emit(Unit) } }
                ),
            )
        )
}