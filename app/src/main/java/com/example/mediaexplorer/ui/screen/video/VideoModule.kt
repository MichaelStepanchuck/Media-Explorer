package com.example.mediaexplorer.ui.screen.video

import org.koin.dsl.module

val videoModule = module {
    factory { VideoPresenter(get()) }
}