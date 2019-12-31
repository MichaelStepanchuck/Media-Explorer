package com.example.mediaexplorer.ui.screen.video

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaexplorer.R
import com.example.mediaexplorer.entity.Video
import com.example.mediaexplorer.util.inflateChildView

class VideoAdapter : RecyclerView.Adapter<VideoViewHolder>() {

    private val videos = mutableListOf<Video>()

    var videoCallback: VideoCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(parent.inflateChildView(R.layout.item_video_content))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position], videoCallback!!)
    }

    override fun getItemCount(): Int = videos.size

    fun setData(newData: List<Video>) {
        val eventDiffUtilCallback = VideoDiffUtilCallback(videos, newData)
        val diffResult = DiffUtil.calculateDiff(eventDiffUtilCallback)
        videos.clear()
        videos.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

}