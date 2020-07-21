package com.example.tmcommonkotlin.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T: ViewModel> vmFactoryFactory(constructor:()->T) : ViewModelProvider.Factory {
    return object: ViewModelProvider.Factory {
        override fun <Y : ViewModel?> create(modelClass: Class<Y>): Y {
            @Suppress("UNCHECKED_CAST")
            return constructor() as Y
        }
    }
}