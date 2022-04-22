package com.tminus1010.exampleclient.ui.imagetotext

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tminus1010.exampleclient.R
import com.tminus1010.exampleclient.databinding.FragImagetotextBinding
import com.tminus1010.exampleclient.ui.all_features.ThrobberSharedVM
import com.tminus1010.tmcommonkotlin.androidx.CreateImageFile
import com.tminus1010.tmcommonkotlin.androidx.extensions.waitForBitmapAndSetUpright
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.coroutines.extensions.use
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ImageToTextFrag : Fragment(R.layout.frag_imagetotext) {
    private lateinit var vb: FragImagetotextBinding
    private val viewModel by viewModels<ImageToTextVM>()

    @Inject
    lateinit var imageToText: ImageToText

    @Inject
    lateinit var createImageFile: CreateImageFile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragImagetotextBinding.bind(view)
        // # Events
        viewModel.takePicture.observe(lifecycleScope) { askTakePictureLauncher.launch(Manifest.permission.CAMERA) }
        // # State
        vb.buttons.bind(viewModel.buttons) { buttons = it }
    }


    private val askTakePictureLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        if (it)
            handlerTakePicture.launch(uriFromFile(createImageFile().also { latestImageFile = it }))
        else
            easyToast("Camera permission is required for this feature")
    }

    private val handlerTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture())
    {
        if (it)
            GlobalScope.launch { imageToText(latestImageFile!!.waitForBitmapAndSetUpright()).logx("imageToText") }.use(ThrobberSharedVM)
        else
            logz("No picture taken")
    }

    private fun uriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(requireContext(), "com.tminus1010.exampleclient.provider", file)
    }

    companion object {
        private var latestImageFile: File? = null
    }
}
