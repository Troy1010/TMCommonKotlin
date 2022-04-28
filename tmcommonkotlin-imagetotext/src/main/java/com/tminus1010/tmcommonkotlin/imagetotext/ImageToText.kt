package com.tminus1010.tmcommonkotlin.imagetotext

import android.app.Application
import android.graphics.Bitmap
import com.googlecode.tesseract.android.TessBaseAPI
import com.tminus1010.tmcommonkotlin.androidx.extensions.rotate
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.asFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ImageToText constructor(private val application: Application) {
    // TODO: Require 1 thread
    suspend operator fun invoke(bitmap: Bitmap): String? {
        suspend fun keepTransformingUntilDropInConfidence(originalBitmap: Bitmap, transformation: (Bitmap) -> Bitmap): Pair<Int, Bitmap> {
            logz("keepTransformingUntilDropInConfidence..")
            var previousConfidence = tesseract.first().apply { setImage(originalBitmap) }.meanConfidence()
            var previousBitmap = originalBitmap
            var done = false
            while (!done) {
                val curBitmap = transformation(previousBitmap)
                val curConfidence = tesseract.first().apply { setImage(curBitmap) }.meanConfidence()
                if (curConfidence < previousConfidence) {
                    logz("keepTransformingUntilDropInConfidence done. previousConfidence:$previousConfidence curConfidence:$curConfidence")
                    done = true
                } else {
                    logz("keepTransformingUntilDropInConfidence continuing. previousConfidence:$previousConfidence curConfidence:$curConfidence")
                    previousConfidence = curConfidence
                    previousBitmap = curBitmap
                }
            }
            return Pair(previousConfidence, previousBitmap)
        }

        val bestAfterFirstDegree =
            listOf(
                keepTransformingUntilDropInConfidence(bitmap) { it.rotate(1f) },
                keepTransformingUntilDropInConfidence(bitmap) { it.rotate(-1f) },
            )
                .maxByOrNull { (confidence, bitmap) -> confidence }!!
        val bestAfterSecondDegree =
            listOf(
                keepTransformingUntilDropInConfidence(bestAfterFirstDegree.second) { it.rotate(0.1f) },
                keepTransformingUntilDropInConfidence(bestAfterFirstDegree.second) { it.rotate(-0.1f) },
            )
                .maxByOrNull { (confidence, bitmap) -> confidence }!!
        return tesseract.first().apply { setImage(bestAfterSecondDegree.second) }.utF8Text
    }

    suspend operator fun invoke(file: File): String? {
        return tesseract.first().apply { setImage(file) }.utF8Text
    }

    private fun copy(inputStream: InputStream, dst: File) {
        inputStream.use { inputStreamRedef ->
            FileOutputStream(dst).use { fileOutputStream ->
                val buf = ByteArray(1024)
                var len: Int
                while (inputStreamRedef.read(buf).also { len = it } > 0) {
                    fileOutputStream.write(buf, 0, len)
                }
            }
        }
    }

    /**
     * Android4Tesseract requires that Tesseract machine-learning model be in tessdata/ folder on device.
     * As a workaround, this initializer copies it from assets to tessdata/.
     * Perhaps, in the future, there will be an easier way to share machine-learning models between applications.
     */
    private suspend fun setupModel() = withContext(Dispatchers.IO) {
        val tessdataFolder = File(application.dataDir, "tessdata")
        val traineddataFile = File(tessdataFolder, "eng.traineddata")
        if (!traineddataFile.exists()) {
            try {
                tessdataFolder.mkdirs()
                copy(application.assets.open("tessdata/eng.traineddata"), traineddataFile)
                logz("Successfully copied eng.traineddata")
            } catch (e: IOException) {
                throw Exception("Error while trying to copy eng.traineddata:", e)
            }
        }
    }

    private val tesseract =
        Observable.create<TessBaseAPI> { downstream ->
            val tesseract = TessBaseAPI()
//            downstream.setCancellable { logz("recycling tessBaseAPI"); tesseract.recycle() } // TODO: Recycle
            runBlocking { setupModel() }
            tesseract.init(application.dataDir.absolutePath, "eng").logx("tesseract.init result")
            downstream.onNext(tesseract)
        }.subscribeOn(Schedulers.computation())
            .replay(1).refCount()
            .asFlow()
}