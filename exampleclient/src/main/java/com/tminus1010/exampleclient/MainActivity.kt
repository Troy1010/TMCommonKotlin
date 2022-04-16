package com.tminus1010.exampleclient

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.tmcommonkotlin.speechtotext.SpeechToText
import com.tminus1010.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.tmcommonkotlin.androidx.CreateImageFile
import com.tminus1010.tmcommonkotlin.androidx.extensions.waitForBitmapAndSetUpright
import com.tminus1010.tmcommonkotlin.coroutines.extensions.doLogx
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import com.tminus1010.tmcommonkotlin.view.extensions.easyVisibility
import kotlinx.coroutines.GlobalScope
import java.io.File

class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainVM>()
    private val imageToText by lazy { ImageToText(application) }
    private val speechToText by lazy { SpeechToText(application) }
    private val createImageFile by lazy { CreateImageFile(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun onStart() {
        super.onStart()
        vb.buttonImagetotext.setOnClickListener { requestTakePicturePermissionResponseHandler.launch(Manifest.permission.CAMERA) }
        vb.buttonPrerecordedSpeechToText.setOnClickListener { ThrobberSharedVM.launch(GlobalScope) { speechToText(application.assets.open("10001-90210-01803.wav"), 16000f).doLogx("speechToText") } }
        // # State
        vb.frameProgressbar.bind(ThrobberSharedVM.isVisible) { easyVisibility = it }
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
        if (it)
            ThrobberSharedVM.launch(GlobalScope) { imageToText(latestImageFile!!.waitForBitmapAndSetUpright()).logx("imageToText") }
        else
            logz("No picture taken")
    }

    private fun uriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(this, "com.tminus1010.exampleclient.provider", file)
    }

    companion object {
        init {
            logz("!*!*! START")
        }

        private var latestImageFile: File? = null
    }
}