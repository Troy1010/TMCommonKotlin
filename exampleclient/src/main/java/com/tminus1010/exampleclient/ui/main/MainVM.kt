package com.tminus1010.exampleclient.ui.main

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MainVM : ViewModel() {
    // # State
    val number =
        Observable.merge(
            Observable.just("1"),
            Observable.just("2").delay(5, TimeUnit.SECONDS),
            Observable.just("3").delay(10, TimeUnit.SECONDS),
            Observable.empty<String>().delay(15, TimeUnit.SECONDS),
        )
            .repeat()
            .replay(1).autoConnect()
}