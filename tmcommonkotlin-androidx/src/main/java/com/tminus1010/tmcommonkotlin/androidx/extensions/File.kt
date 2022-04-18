package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
fun File.waitForBitmapAndSetUpright(): Bitmap {
    var bitmap: Bitmap? = null
    while (bitmap == null) bitmap = BitmapFactory.decodeFile(this.absolutePath)
    return when (ExifInterface(this).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> bitmap.rotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> bitmap.rotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> bitmap.rotate(270f)
        else -> bitmap
    }
}