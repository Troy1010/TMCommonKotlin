package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.view.ViewGroup

fun ViewGroup.removeAllViewsAndSetTag() {
    isRemovingViews = true
    removeAllViews()
    isRemovingViews = false
}