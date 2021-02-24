package com.tminus1010.tmcommonkotlin.misc.extensions

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.tminus1010.tmcommonkotlin.misc.createViewModelFactory
import java.util.HashSet

inline fun <reified T: ViewModel> AppCompatActivity.scopeVMToDestinations(
    navController: NavController,
    destinations: HashSet<Int>
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.id !in destinations) {
            this.viewModelStore.remove<T>()
        }
    }
}

inline fun <reified VM : ViewModel> AppCompatActivity.viewModels2(noinline function: () -> VM) =
    this.viewModels<VM> { createViewModelFactory(function) }