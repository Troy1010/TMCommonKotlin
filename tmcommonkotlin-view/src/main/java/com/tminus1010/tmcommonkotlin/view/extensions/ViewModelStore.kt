package com.tminus1010.tmcommonkotlin.view.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import java.util.HashMap

inline fun <reified T> ViewModelStore.remove() {
    val vmStoreField = ViewModelStore::class.java.getDeclaredField("mMap")
    vmStoreField.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    val vmStoreMap = vmStoreField.get(this) as HashMap<*, ViewModel>
    var keyToDelete:String?=null
    for ((key, vmStore) in vmStoreMap) {
        if (vmStore is T) {
            keyToDelete = key.toString()
            break
        }
    }
    if (keyToDelete!=null) {
        val methodField = ViewModel::class.java.getDeclaredMethod("onCleared")
        methodField.isAccessible = true
        methodField.invoke(vmStoreMap[keyToDelete])
        vmStoreMap.remove(keyToDelete)
    }
}