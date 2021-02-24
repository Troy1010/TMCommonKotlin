package com.tminus1010.tmcommonkotlin.misc.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(msg:String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, lengthID).show()
}