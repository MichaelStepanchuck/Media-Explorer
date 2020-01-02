package com.example.mediaexplorer

import android.app.Application
import com.example.mediaexplorer.di.repositoryModule
import com.example.mediaexplorer.ui.screen.audio.audioModule
import com.example.mediaexplorer.ui.screen.main.mainModule
import com.example.mediaexplorer.ui.screen.video.videoModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MediaExplorerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@MediaExplorerApplication)
            modules(
                listOf(
                    audioModule,
                    videoModule,
                    mainModule,
                    repositoryModule
                )
            )
        }
    }

}

