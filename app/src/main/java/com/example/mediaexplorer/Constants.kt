package com.example.mediaexplorer

import android.Manifest

object Constants {
    const val APPLICATION_TAG = "Media Explorer"
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
}