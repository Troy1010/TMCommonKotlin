package com.tminus1010.exampleclient.ui.imagetotext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tminus1010.exampleclient.R
import com.tminus1010.exampleclient.databinding.FragImagetotextBinding
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageToTextFrag : Fragment(R.layout.frag_imagetotext) {
    private lateinit var vb: FragImagetotextBinding
    private val viewModel by viewModels<ImageToTextVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragImagetotextBinding.bind(view)
        // #
        // # State
        vb.buttons.bind(viewModel.buttons) { buttons = it }
    }


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
}
