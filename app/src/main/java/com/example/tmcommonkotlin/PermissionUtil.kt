package com.example.tmcommonkotlin

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


val permissibleActions = HashMap<Int, PermissibleAction>()

data class PermissibleAction(
    val permissions: Array<String>,
    val code: Int,
    val action: () -> Unit
)

fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
    for (grantResult in grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            return false
        }
    }
    return true
}
