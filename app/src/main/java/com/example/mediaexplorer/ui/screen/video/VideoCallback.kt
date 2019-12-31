package com.example.mediaexplorer.ui.screen.video

import com.example.mediaexplorer.entity.Video

interface VideoCallback {
    fun onVideoListItemClick(video: Video)
}