package com.tminus1010.tmcommonkotlin.androidx.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.clearItemDecorations() =
    ((this.itemDecorationCount - 1) downTo 0).forEach { this.removeItemDecorationAt(it) }