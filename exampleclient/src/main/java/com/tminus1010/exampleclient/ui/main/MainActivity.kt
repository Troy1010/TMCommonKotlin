package com.tminus1010.exampleclient.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tminus1010.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.exampleclient.ui.all_features.ThrobberSharedVM
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.view.extensions.easyVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.frameProgressbar.bind(ThrobberSharedVM.isVisible) { easyVisibility = it }
    }

//    override fun onStart() {
//        super.onStart()
//        vb.buttonImagetotext.setOnClickListener { handlerAskTakePicture.launch(Manifest.permission.CAMERA) }
//        vb.buttonPrerecordedSpeechToText.setOnClickListener { GlobalScope.launch { speechToText(application.assets.open("10001-90210-01803.wav"), 16000f).doLogx("speechToText") }.use(ThrobberSharedVM) }
//        vb.buttonOpenMicAndPlayback.setOnClickListener { handlerAskRecordAudioForPlayback.launch(Manifest.permission.RECORD_AUDIO) }
//        vb.buttonOpenMicForSpeechToText.setOnClickListener { handlerAskRecordAudioForSpeechToText.launch(Manifest.permission.RECORD_AUDIO) }
//        // # State
//        vb.frameProgressbar.bind(ThrobberSharedVM.isVisible) { easyVisibility = it }
//        vb.tvNumber.bind(viewModel.number) { text = it }
//    }
//
//    private val handlerAskTakePicture = registerForActivityResult(ActivityResultContracts.RequestPermission())
//    {
//        if (it)
//            handlerTakePicture.launch(uriFromFile(createImageFile().also { latestImageFile = it }))
//        else
//            easyToast("Camera permission is required for this feature")
//    }
//
//    private val handlerTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture())
//    {
//        if (it)
//            GlobalScope.launch { imageToText(latestImageFile!!.waitForBitmapAndSetUpright()).logx("imageToText") }.use(ThrobberSharedVM)
//        else
//            logz("No picture taken")
//    }
//
//    private val handlerAskRecordAudioForPlayback = registerForActivityResult(ActivityResultContracts.RequestPermission())
//    {
//        if (it)
//            GlobalScope.launch { openMicAndPlayback(closeMicSignal = Observable.just(Unit).delay(3, TimeUnit.SECONDS)) }.use(ThrobberSharedVM)
//        else
//            easyToast("Microphone permission is required for this feature")
//    }
//
//    private val handlerAskRecordAudioForSpeechToText = registerForActivityResult(ActivityResultContracts.RequestPermission())
//    {
//        if (it)
//            openMicForSpeechToText().doLogx("speechToText").let { ThrobberSharedVM.decorate(it) }.observe(GlobalScope)
//        else
//            easyToast("Microphone permission is required for this feature")
//    }
//
//    private fun uriFromFile(file: File): Uri {
//        return FileProvider.getUriForFile(this, "com.tminus1010.exampleclient.provider", file)
//    }
//
//    companion object {
//        private var latestImageFile: File? = null
//    }
}