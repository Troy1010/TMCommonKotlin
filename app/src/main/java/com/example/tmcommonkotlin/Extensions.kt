package com.example.tmcommonkotlin

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

val <T> T.exhaustive: T
    get() = this


fun Context.easyToast(msg:String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, lengthID).show()
}

fun Fragment.easyToast(msg: String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), msg, lengthID).show()
}


fun String.isAllDigits(): Boolean {
    for (ch in this) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}
fun String.noDoubleSpaces():String {
    return if (this.contains("  ")) {
        this.replace("  ", " ").noDoubleSpaces()
    } else {
        this
    }
}


fun String.hasDigit(): Boolean {
    for (ch in this) {
        if (ch.isDigit()) {
            return true
        }
    }
    return false
}