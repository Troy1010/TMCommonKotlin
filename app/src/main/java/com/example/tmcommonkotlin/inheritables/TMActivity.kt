package com.example.grocerygo.activities_and_frags.Inheritables

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tmcommonkotlin.*

abstract class TMActivity(val layout: Int? = null, val theme: Int? = null) :
    AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme?.run { setTheme(theme) }
        layout?.run { setContentView(layout) }
    }

    // helps tryPermissibleAction
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        for (permissibleAction in permissibleActions.values) {
            if (permissibleAction.code == requestCode) {
                if ((grantResults.isNotEmpty()) and hasAllPermissionsGranted(grantResults)) {
                    permissibleAction.action()
                } else {
                    easyToast(this, "Action requires permissions", Toast.LENGTH_LONG)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    fun tryPermissibleAction(
        permissions: Array<String>,
        code: Int,
        action: () -> Unit
    ) {
        tryPermissibleAction(
            PermissibleAction(
                permissions, code, action
            )
        )
    }

    fun tryPermissibleAction(permissibleAction: PermissibleAction) {
        // *Don't forget manifest
        // save code, action, permissions in permissibleActions
        permissibleActions[permissibleAction.code] = permissibleAction
        //
        if (permissibleAction.permissions.isEmpty()) {
            permissibleAction.action()
            return
        }
        val listPermission: ArrayList<String> = ArrayList()
        for (permission in permissibleAction.permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            )
                listPermission.add(permission)
        }
        if (listPermission.isEmpty()) {
            permissibleAction.action()
            return
        }
        ActivityCompat.requestPermissions(
            this,
            listPermission.toTypedArray(),
            permissibleAction.code
        )
    }


    fun easyPhoto(code:Int) {
        val takePhoto = {
            startActivityForResult(
                Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                code
            )
        }
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )
        tryPermissibleAction(
            PermissibleAction(permissions, code, takePhoto)
        )
    }
}