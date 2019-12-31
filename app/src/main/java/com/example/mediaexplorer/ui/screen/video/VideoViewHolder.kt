package com.example.mediaexplorer.ui.screen.video

import android.text.format.Formatter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediaexplorer.entity.Video
import com.example.mediaexplorer.util.DateTimeFormatters.Companion.HOURS_MINUTES_SECONDS_FORMATTER
import com.example.mediaexplorer.util.DateTimeFormatters.Companion.MINUTES_SECONDS_FORMATTER
import com.example.mediaexplorer.util.format
import kotlinx.android.synthetic.main.item_video_content.view.*
import org.threeten.bp.Duration

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal fun bind(video: Video, videoCallback: VideoCallback) {
        itemView.apply {
            Glide.with(context).load(video.path).into(videoPreviewImageView)
            videoNameTextView.text = video.name
            videoDurationTextView.text = formatDuration(video.duration)
            videoSizeTextView.text = Formatter.formatShortFileSize(context, video.size)
            outsideContainer.setOnClickListener { videoCallback.onVideoListItemClick(video) }
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