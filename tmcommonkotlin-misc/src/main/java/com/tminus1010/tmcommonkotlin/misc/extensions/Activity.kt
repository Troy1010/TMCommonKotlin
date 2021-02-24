package com.tminus1010.tmcommonkotlin.misc.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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