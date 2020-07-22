package com.example.tmcommonkotlin

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import java.util.HashMap
import java.util.HashSet

fun <T: ViewModel> vmFactoryFactory(constructor:()->T) : ViewModelProvider.Factory {
    return object: ViewModelProvider.Factory {
        override fun <Y : ViewModel?> create(modelClass: Class<Y>): Y {
            @Suppress("UNCHECKED_CAST")
            return constructor() as Y
        }
    }
}

// Experimental
inline fun <reified T:ViewModel> AppCompatActivity.scopeVMToDestinations(
    navController: NavController,
    destinations: HashSet<Int>
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.id !in destinations) {
            this.viewModelStore.remove<T>()
        }
    }
}

inline fun <reified T> ViewModelStore.remove() {
    val VMStoreField = ViewModelStore::class.java.getDeclaredField("mMap")
    VMStoreField.isAccessible = true
    val VMStoreMap = VMStoreField.get(this) as HashMap<*, ViewModel>
    var keyToDelete:String?=null
    VMStoreMap.map {
        if (it.value is T) {
            keyToDelete = it.key.toString()
        }
        it
    }
    if (keyToDelete!=null) {
        val methodField = ViewModel::class.java.getDeclaredMethod("onCleared")
        methodField.isAccessible = true
        methodField.invoke(VMStoreMap[keyToDelete])
        VMStoreMap.remove(keyToDelete)
    }
}