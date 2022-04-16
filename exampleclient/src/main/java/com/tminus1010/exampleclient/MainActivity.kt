package com.tminus1010.exampleclient

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.tminus1010.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.tmcommonkotlin.androidx.CreateImageFile
import com.tminus1010.tmcommonkotlin.androidx.extensions.waitForBitmapAndSetUpright
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import kotlinx.coroutines.runBlocking
import java.io.File

class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainVM>()
    private var latestImageFile: File? = null
    private val imageToText by lazy { ImageToText(application) }
    private val createImageFile by lazy { CreateImageFile(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun onStart() {
        super.onStart()
        vb.buttonImagetotext.setOnClickListener { requestTakePicturePermissionResponseHandler.launch(Manifest.permission.CAMERA) }
        // # State
        vb.tvNumber.bind(viewModel.number) { text = it }
    }

    private val requestTakePicturePermissionResponseHandler = registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        if (it)
            takePictureForImageToTextResponseHandler.launch(uriFromFile(createImageFile().also { latestImageFile = it }))
        else
            easyToast("Camera permission is required for this feature")
    }

    private val takePictureForImageToTextResponseHandler = registerForActivityResult(ActivityResultContracts.TakePicture())
    {
        if (it) runBlocking { imageToText(latestImageFile!!.waitForBitmapAndSetUpright()).logx("imageToText") }
    }

    private fun uriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(this, "com.tminus1010.exampleclient.provider", file)
    }
}