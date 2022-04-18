package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.LayoutManager.children : Iterable<View>
    get() = (0 until this.childCount).mapNotNull { this.getChildAt(it) }