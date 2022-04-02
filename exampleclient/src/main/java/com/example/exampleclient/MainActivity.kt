package com.example.exampleclient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.exampleclient.databinding.ActivityMainBinding
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import com.tminus1010.tmcommonkotlin.rx.extensions.observe
import com.tminus1010.tmcommonkotlin.view.extensions.easyToast
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val chooseFileResultHandler = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            easyToast("chooseFileLauncher RESULT_OK")
        }
    }

    private fun launchChooseFile() {
        logz("launchChooseFile")
        chooseFileResultHandler.launch(
            Intent().apply { type = "*/*"; action = Intent.ACTION_GET_CONTENT }
                .let { Intent.createChooser(it, "Select transactions csv") }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logz("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        // # Events
        ViewModel.navToChooseFile.observe(this) { launchChooseFile() }
        // # State
        vb.tv.bind(ViewModel.state) { text = it }
    }

    override fun onDestroy() {
        super.onDestroy()
        logz("onDestroy")
    }

    object ViewModel {
        val state =
            Observable.merge(
                Observable.just("1"),
                Observable.just("2").delay(5, TimeUnit.SECONDS),
                Observable.just("3").delay(10, TimeUnit.SECONDS),
                Observable.empty<String>().delay(15, TimeUnit.SECONDS),
            )
                .repeat()
                .doOnNext { logz("state:$it") }
                .replay(1).autoConnect()
        val navToChooseFile =
            Observable.just(Unit).delay(11, TimeUnit.SECONDS)
    }
}