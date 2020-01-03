package com.example.mediaexplorer.di

import android.content.Context
import com.example.mediaexplorer.repository.AudioRepository
import com.example.mediaexplorer.repository.VideoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { VideoRepository(get()) }
    single { AudioRepository(get()) }
    single { provideContentResolver(get()) }
}

private fun provideContentResolver(context: Context) = context.contentResolver