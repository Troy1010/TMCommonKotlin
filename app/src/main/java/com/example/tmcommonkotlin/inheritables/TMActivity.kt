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

abstract class TMActivity(val layout: Int?=null, val theme: Int? = null) :
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
                if ((!grantResults.isEmpty()) and hasAllPermissionsGranted(grantResults)) {
                    permissibleAction.action()
                } else {
                    easyToast(this, "Action requires permissions", Toast.LENGTH_LONG)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    val takePhoto = {
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE),
            CAMERA_REQUEST_CODE
        )
    }

    fun easyPhoto() {
        tryPermissibleAction(this,
        PermissibleAction(arrayOf(
            Manifest.permission.CAMERA
        ),
        CAMERA_REQUEST_CODE,
        takePhoto)
        )
        // *Remember Manifest: <uses-permission android:name="android.permission.CAMERA"/>
//        val permission = ContextCompat.checkSelfPermission(
//            this, Manifest.permission.CAMERA
//        )
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.CAMERA),
//                CAMERA_REQUEST_CODE
//            )
//        } else {
//            takePhoto()
//        }
    }
}