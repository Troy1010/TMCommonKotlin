package com.tminus1010.tmcommonkotlin.misc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T: ViewModel> createViewModelFactory(actionProvideVM:()->T) : ViewModelProvider.Factory {
    return object: ViewModelProvider.Factory {
        override fun <Y : ViewModel?> create(modelClass: Class<Y>): Y {
            @Suppress("UNCHECKED_CAST")
            return actionProvideVM() as Y
        }
    }
}
