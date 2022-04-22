package com.tminus1010.exampleclient.ui.speechtotext

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tminus1010.exampleclient.R
import com.tminus1010.exampleclient.databinding.FragSpeechtotextBinding
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpeechToTextFrag : Fragment(R.layout.frag_speechtotext) {
    private lateinit var vb: FragSpeechtotextBinding
    private val viewModel by viewModels<SpeechToTextVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragSpeechtotextBinding.bind(view)
        // # Events
        viewModel.askRecordAudioForSpeechToText.observe(lifecycleScope) { askRecordAudioForSpeechToTextLauncher.launch(Manifest.permission.RECORD_AUDIO) }
        // # State
        vb.textview.bind(viewModel.speechToTextString) { text = it }
        vb.buttons.bind(viewModel.buttons) { buttons = it }
    }

    private val askRecordAudioForSpeechToTextLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        if (it)
            viewModel.recordAudioForSpeechToTextGranted()
        else
            easyToast("Microphone permission is required for this feature")
    }
}
