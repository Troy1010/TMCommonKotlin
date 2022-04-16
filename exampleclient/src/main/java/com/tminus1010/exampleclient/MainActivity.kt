package com.tminus1010.exampleclient

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.tminus1010.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import com.tminus1010.tmcommonkotlin.misc.generateUniqueID
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import kotlinx.coroutines.runBlocking
import java.io.File

class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainVM>()
    private var latestImageFile: File? = null
    private val imageToText by lazy { ImageToText(application) }

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
        if (it) {
            runBlocking { imageToText(latestImageFile!!.waitForBitmapAndSetUpright()) }
        }
    }

    fun Bitmap.rotate(degrees: Float) =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)

    fun File.waitForBitmapAndSetUpright(): Bitmap {
        var bitmap: Bitmap? = null
        while (bitmap == null) bitmap = BitmapFactory.decodeFile(this.absolutePath)
        return when (ExifInterface(this).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> bitmap.rotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> bitmap.rotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> bitmap.rotate(270f)
            else -> bitmap
        }!!
    }

    private fun createImageFile(): File {
        return File.createTempFile(
            "JPEG_${generateUniqueID()}_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!,
        )
    }

    private fun uriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(
            this,
            "com.tminus1010.budgetvalue.provider",
            file,
        )
    }
}