package com.example.tmcommonkotlin.microphone.extensions

import java.io.File

fun File.toByteArray() = org.apache.commons.io.FileUtils.readFileToByteArray(this)