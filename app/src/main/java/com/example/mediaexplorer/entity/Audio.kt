package com.example.mediaexplorer.entity

import org.threeten.bp.Duration

data class Audio(
    val id: Int,
    val artist: String,
    val title: String,
    val path: String,
    val duration: Duration,
    val size: Long
)