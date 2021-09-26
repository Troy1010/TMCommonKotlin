package com.tminus1010.tmcommonkotlin.view.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import java.util.*

/**
 * @return the previous value associated with the key, or `null` if the key was not present in the map.
 */
@RequiresApi(Build.VERSION_CODES.O)
inline fun <reified VM : ViewModel> ViewModelStore.remove(): ViewModel? {
    @Suppress("UNCHECKED_CAST")
    val map = this::class.java.declaredFields
        .find { it.type.isInstance(hashMapOf<String, ViewModel>()) }!!
        .also { it.isAccessible = true }
        .get(this) as HashMap<String, ViewModel>
    val key = map.entries
        .find { it.value is VM }
        ?.key
    return map.remove(key)
        // The contract is that .clear() is called when a ViewModel is removed.
        ?.also { viewModel ->
            // Finding .clear() is not straightforward, but we can guess. Finding it by name does not work with obfuscation.
            ViewModel::class.java.declaredMethods
                .first { it.parameterCount == 0 && it.returnType == Void.TYPE }
                .also { it.isAccessible = true }
                .invoke(viewModel)
        }
}