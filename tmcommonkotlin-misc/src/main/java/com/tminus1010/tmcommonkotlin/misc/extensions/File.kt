package com.tminus1010.tmcommonkotlin.misc.extensions

import java.io.File

fun File.toByteArray() = org.apache.commons.io.FileUtils.readFileToByteArray(this)