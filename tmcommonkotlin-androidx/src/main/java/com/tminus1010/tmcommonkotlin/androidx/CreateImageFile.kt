package com.tminus1010.tmcommonkotlin.androidx

import android.app.Application
import android.os.Environment
import com.tminus1010.tmcommonkotlin.misc.generateUniqueID
import java.io.File

class CreateImageFile constructor(private val application: Application) {
    operator fun invoke(): File {
        return File.createTempFile("JPEG_${generateUniqueID()}_", ".jpg", application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
    }
}