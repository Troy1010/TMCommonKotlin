package com.example.tmcommonkotlin

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


val permissibleActions = HashMap<Int, PermissibleAction>()

data class PermissibleAction(
    val permissions: Array<String>,
    val code:Int,
    val action: () -> Unit
)

fun tryPermissibleAction(activity: Activity, permissibleAction: PermissibleAction) {
    tryPermissibleAction(activity, permissibleAction.permissions,
        permissibleAction.code, permissibleAction.action)
}
fun tryPermissibleAction(activity: Activity, permissions: Array<String>, code: Int, action: () -> Unit) {
    // save code, action, permissions in permissibleActions
    if (!permissibleActions.containsKey(code)) {
        permissibleActions[code] = PermissibleAction(permissions, code, action)
    }
    // *Don't forget manifest
    if (permissions.isEmpty()) {
        action()
        return
    }
    val listPermission: ArrayList<String> = ArrayList()
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        )
            listPermission.add(permission)
    }
    if (listPermission.isEmpty()) {
        action()
        return
    }
    ActivityCompat.requestPermissions(
        activity,
        listPermission.toTypedArray(),
        code
    )
}

fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
    for (grantResult in grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            return false
        }
    }
    return true
}
