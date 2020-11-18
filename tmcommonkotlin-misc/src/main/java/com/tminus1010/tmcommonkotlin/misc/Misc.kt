package com.tminus1010.tmcommonkotlin.misc

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


fun getToday() : String {
    return SimpleDateFormat("yyyy-MM-dd").format(java.util.Calendar.getInstance().getTime()).toString()
}

fun easySnackbar(
    coordinatorLayout: CoordinatorLayout,
    msg: String,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    var sb = Snackbar
        .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
    if ((actionText != null) and (action != null)) {
        sb.setAction(actionText, action)
    }
    sb.show()
}

//fun invokeHiddenMethod(name: String) {
//    val method = sut.javaClass.getDeclaredMethod(name)
//    method.isAccessible = true
//    method.invoke(testSubject)
//}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
}

fun generateUniqueID(): String {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase()
}