package com.example.mediaexplorer.entity

import org.threeten.bp.Duration

data class Video(
    val id: Int,
    val name: String,
    val path: String,
    val duration: Duration,
    val size: Long
)