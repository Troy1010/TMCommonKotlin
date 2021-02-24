package com.tminus1010.tmcommonkotlin.misc.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String, lengthID:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), msg, lengthID).show()
}