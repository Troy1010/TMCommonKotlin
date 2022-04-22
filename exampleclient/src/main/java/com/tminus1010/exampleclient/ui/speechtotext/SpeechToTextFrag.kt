package com.tminus1010.exampleclient.ui.speechtotext

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tminus1010.exampleclient.R
import com.tminus1010.exampleclient.databinding.FragImagetotextBinding
import com.tminus1010.exampleclient.ui.all_features.ThrobberSharedVM
import com.tminus1010.tmcommonkotlin.coroutines.extensions.doLogx
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.speechtotext.OpenMicForSpeechToText
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject

@AndroidEntryPoint
class SpeechToTextFrag : Fragment(R.layout.frag_speechtotext) {
    private lateinit var vb: FragImagetotextBinding
    private val viewModel by viewModels<SpeechToTextVM>()

    @Inject
    lateinit var openMicForSpeechToText: OpenMicForSpeechToText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragImagetotextBinding.bind(view)
        // # Events
        viewModel.openMic.observe(lifecycleScope) { askRecordAudioForSpeechToTextLauncher.launch(Manifest.permission.RECORD_AUDIO) }
        // # State
        vb.buttons.bind(viewModel.buttons) { buttons = it }
    }

    private val askRecordAudioForSpeechToTextLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        if (it)
            openMicForSpeechToText().doLogx("speechToText").let { ThrobberSharedVM.decorate(it) }.observe(GlobalScope)
        else
            easyToast("Microphone permission is required for this feature")
    }
}
