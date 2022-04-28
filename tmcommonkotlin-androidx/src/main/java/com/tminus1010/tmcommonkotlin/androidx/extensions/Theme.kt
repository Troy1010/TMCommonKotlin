package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.ColorInt

@ColorInt
fun Resources.Theme.getColorByAttr(attrID: Int): Int {
    return TypedValue()
        .also { resolveAttribute(attrID, it, true) }
        .data
}