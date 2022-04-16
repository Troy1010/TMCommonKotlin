package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.rotate(degrees: Float): Bitmap {
    return Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)
}