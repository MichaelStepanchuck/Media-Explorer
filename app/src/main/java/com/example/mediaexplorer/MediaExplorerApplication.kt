package com.example.mediaexplorer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MediaExplorerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}

