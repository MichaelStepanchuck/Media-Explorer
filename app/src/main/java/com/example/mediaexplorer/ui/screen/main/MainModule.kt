package com.example.mediaexplorer.ui.screen.main

import org.koin.dsl.module

val mainModule = module {
    factory { MainPresenter() }
}