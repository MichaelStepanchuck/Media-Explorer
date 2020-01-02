package com.example.mediaexplorer.ui.screen.audio

import org.koin.dsl.module

val audioModule = module {
    factory { AudioPresenter(get()) }
}