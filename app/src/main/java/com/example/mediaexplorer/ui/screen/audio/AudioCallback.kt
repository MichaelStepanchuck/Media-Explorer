package com.example.mediaexplorer.ui.screen.audio

import com.example.mediaexplorer.entity.Audio

interface AudioCallback {
    fun onAudioListItemClick(audio: Audio)
}