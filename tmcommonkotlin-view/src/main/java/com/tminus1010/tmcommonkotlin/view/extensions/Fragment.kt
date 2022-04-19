package com.tminus1010.tmcommonkotlin.view.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

@Deprecated("use easyToast")
fun Fragment.toast(msg: String, lengthID: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, lengthID).show()
}

fun Fragment.easyToast(msg: String, lengthID: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), msg, lengthID).show()
}

inline val Fragment.nav
    get() = findNavController()

inline val Fragment.v
    get() = requireView()