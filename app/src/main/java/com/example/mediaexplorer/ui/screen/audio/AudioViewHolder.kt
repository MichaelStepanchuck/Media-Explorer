package com.example.mediaexplorer.ui.screen.audio

import android.text.format.Formatter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaexplorer.entity.Audio
import com.example.mediaexplorer.util.DateTimeFormatters.Companion.HOURS_MINUTES_SECONDS_FORMATTER
import com.example.mediaexplorer.util.DateTimeFormatters.Companion.MINUTES_SECONDS_FORMATTER
import com.example.mediaexplorer.util.format
import kotlinx.android.synthetic.main.item_audio_content.view.*
import org.threeten.bp.Duration

class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal fun bind(audio: Audio, audioCallback: AudioCallback) {
        itemView.apply {
            audioTitleTextView.text = audio.title
            audioArtistTextView.text = audio.artist
            audioDurationTextView.text = formatDuration(audio.duration)
            audioSizeTextView.text = Formatter.formatShortFileSize(context, audio.size)
            outsideContainer.setOnClickListener { audioCallback.onAudioListItemClick(audio) }
        }
    }

    private fun formatDuration(duration: Duration): String {
        return if (duration.toMinutes() > MINUTES_IN_HOUR) {
            HOURS_MINUTES_SECONDS_FORMATTER.format(duration)
        } else {
            MINUTES_SECONDS_FORMATTER.format(duration)
        }
    }

    companion object {
        private const val MINUTES_IN_HOUR = 60
    }

}