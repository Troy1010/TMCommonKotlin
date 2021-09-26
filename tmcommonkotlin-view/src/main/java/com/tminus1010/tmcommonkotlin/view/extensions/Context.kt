package com.tminus1010.tmcommonkotlin.view.extensions

import android.content.Context
import android.widget.Toast

@Deprecated("use easyToast", ReplaceWith("easyToast(msg, lengthID)"))
fun Context.toast(msg: String, lengthID: Int = Toast.LENGTH_SHORT) =
    easyToast(msg, lengthID)

fun Context.easyToast(msg: String, lengthID: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, lengthID).show()