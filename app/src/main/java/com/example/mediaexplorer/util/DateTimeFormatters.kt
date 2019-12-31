package com.example.mediaexplorer.util

import org.threeten.bp.format.DateTimeFormatter

class DateTimeFormatters {
    companion object {
        val HOURS_MINUTES_SECONDS_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("H'h' m'm' s's'")
        val MINUTES_SECONDS_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("m'm' s's'")
    }
}