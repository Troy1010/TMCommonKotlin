package com.example.tmcommonkotlin

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

fun logz (msg:String) {
    Log.d("TMLog", "TM`$msg")
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