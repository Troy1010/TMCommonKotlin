package com.tminus1010.tmcommonkotlin.misc.tmTableView

import android.view.View
import androidx.viewbinding.ViewBinding

interface IViewItemRecipe3 {
    val intrinsicWidth: Int
    val intrinsicHeight: Int
    fun createVB(): ViewBinding
    fun bind(vb: ViewBinding)
    fun createImpatientlyBoundView(): View
    fun intrinsicHeight(width: Int): Int
}