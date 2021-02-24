package com.tminus1010.tmcommonkotlin.view.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.tminus1010.tmcommonkotlin.view.createViewModelFactory

fun Fragment.toast(msg: String, lengthID: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, lengthID).show()
}

inline fun <reified VM : ViewModel> Fragment.activityViewModels2(noinline function: () -> VM): Lazy<VM> {
    return this.activityViewModels { createViewModelFactory(function) }
}

inline fun <reified VM : ViewModel> Fragment.viewModels2(noinline function: () -> VM): Lazy<VM> {
    return this.viewModels { createViewModelFactory(function) }
}

inline val Fragment.nav
    get() = findNavController()

inline val Fragment.v
    get() = requireView()