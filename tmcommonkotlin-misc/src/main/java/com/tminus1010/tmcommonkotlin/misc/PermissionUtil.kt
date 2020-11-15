package com.tminus1010.tmcommonkotlin.misc

import android.content.Intent
import android.content.pm.PackageManager


val permissibleActions = HashMap<Int, PermissibleAction>()

data class PermissibleAction(
    val permissions: Array<String>,
    val code: Int,
    val startingAction: () -> Unit,
    val resultHandlingAction: ((intent: Intent?) -> Unit)?=null
)

fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
    return grantResults.all { it != PackageManager.PERMISSION_DENIED }
}
