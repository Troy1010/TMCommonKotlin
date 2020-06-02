package com.example.grocerygo.activities_and_frags.Inheritables

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tmcommonkotlin.CAMERA_REQUEST_CODE
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz

abstract class TMActivity(val layout: Int?=null, val theme: Int? = null) :
    AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme?.run { setTheme(theme) }
        layout?.run { setContentView(layout) }
    }

    // helps easyPhoto
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((!grantResults.isEmpty()) and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    takePhoto()
                } else {
                    easyToast(this, "Action requires Camera permissions")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun takePhoto() {
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE),
            CAMERA_REQUEST_CODE
        )
    }

    fun easyPhoto() {
        // *Remember Manifest: <uses-permission android:name="android.permission.CAMERA"/>
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            takePhoto()
        }
    }
}