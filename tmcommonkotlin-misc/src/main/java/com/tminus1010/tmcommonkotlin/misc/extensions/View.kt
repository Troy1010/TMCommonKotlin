package com.tminus1010.tmcommonkotlin.misc.extensions

import android.view.View
import android.view.ViewGroup

fun View.easySetHeight(height: Int) =
    this.apply { easyGetLayoutParams().height = height }

fun View.easySetWidth(width: Int) =
    this.apply { easyGetLayoutParams().width = width }

fun View.easyGetLayoutParams() =
    this.layoutParams ?: ViewGroup.LayoutParams(-1, -1).also { this.layoutParams = it }

fun View.measureUnspecified() =
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

fun View.measureExact() =
    measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)