package com.example.exampleclient

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.tmcommonkotlin.misc.extensions.bind

class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun onStart() {
        super.onStart()
        // # State
        vb.tv.bind(viewModel.number) { text = it }
    }
}