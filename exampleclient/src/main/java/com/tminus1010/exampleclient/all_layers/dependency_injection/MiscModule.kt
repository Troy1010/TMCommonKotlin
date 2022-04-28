package com.tminus1010.exampleclient.all_layers.dependency_injection

import android.app.Application
import com.tminus1010.tmcommonkotlin.androidx.CreateImageFile
import com.tminus1010.tmcommonkotlin.imagetotext.ImageToText
import com.tminus1010.tmcommonkotlin.microphone.OpenMicAndPlayback
import com.tminus1010.tmcommonkotlin.speechtotext.ModelProvisionStrategy
import com.tminus1010.tmcommonkotlin.speechtotext.OpenMicForSpeechToText
import com.tminus1010.tmcommonkotlin.speechtotext.SpeechToText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MiscModule {
    @Provides
    fun provideImageToText(application: Application): ImageToText = ImageToText(application)

    @Provides
    fun provideSpeechToText(application: Application): SpeechToText = SpeechToText(application, ModelProvisionStrategy.EXTERNAL_VOSK)

    @Provides
    fun provideCreateImageFile(application: Application): CreateImageFile = CreateImageFile(application)

    @Provides
    fun provideOpenMicAndPlayback(application: Application): OpenMicAndPlayback = OpenMicAndPlayback(application)

    @Provides
    fun provideOpenMicForSpeechToText(application: Application): OpenMicForSpeechToText = OpenMicForSpeechToText(application)
}