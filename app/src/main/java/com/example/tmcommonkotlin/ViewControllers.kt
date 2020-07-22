package com.example.tmcommonkotlin

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.easyToast(msg:String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, lengthID).show()
}

fun Fragment.easyToast(msg: String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), msg, lengthID).show()
}