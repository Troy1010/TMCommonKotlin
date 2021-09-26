package com.tminus1010.tmcommonkotlin.view.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Int.toDP(context: Context): Int =
    this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Int.toPX(context: Context): Int =
    this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)