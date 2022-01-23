package com.tminus1010.tmcommonkotlin.view.extensions

import android.view.View

var View.easyVisibility: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }