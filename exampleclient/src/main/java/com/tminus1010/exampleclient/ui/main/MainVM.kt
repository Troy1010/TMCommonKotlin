package com.tminus1010.exampleclient.ui.main

import androidx.lifecycle.ViewModel
import com.tminus1010.exampleclient.ui.all_features.vm_item.ButtonVMItem
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

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
                ButtonVMItem(
                    title = "Image To Text",
                    titleTextSize = 32f,
                    onClick = { runBlocking { navToImageToText.emit(Unit) } }
                ),
                ButtonVMItem(
                    title = "Image To Text",
                    titleTextSize = 32f,
                    onClick = { runBlocking { navToImageToText.emit(Unit) } }
                ),
            )
        )
}