package com.example.mediaexplorer.di

import com.example.mediaexplorer.repository.AudioRepository
import com.example.mediaexplorer.repository.VideoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { VideoRepository(get()) }
    single { AudioRepository(get()) }
}
