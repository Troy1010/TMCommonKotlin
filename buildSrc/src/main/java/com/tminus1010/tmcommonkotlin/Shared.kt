package com.tminus1010.tmcommonkotlin

import org.gradle.api.JavaVersion

object Shared {
    const val groupId = "com.tminus1010.tmcommonkotlin"
    const val targetSDK = 31
    const val minSDK = 22
    @JvmStatic
    val java = JavaVersion.VERSION_11
}