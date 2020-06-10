package com.example.tmcommonkotlin

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat


fun getToday() : String {
    return SimpleDateFormat("yyyy-MM-dd").format(java.util.Calendar.getInstance().getTime()).toString()
}

fun shortClassName(className:String):String {
    val periodMatches = Regex("""\..""").findAll(className)
    if (periodMatches.count()==0)
        return className
    val periodPos = periodMatches.last().range.last
    return className.substring(periodPos)
}

fun easyToast(context: Context, msg: String, lengthID:Int=Toast.LENGTH_SHORT) {
    Toast.makeText(context, msg, lengthID).show()
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
        MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
    return Uri.parse(path)
}