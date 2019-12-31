package com.example.mediaexplorer.ui.screen.audio

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaexplorer.R
import com.example.mediaexplorer.entity.Audio
import com.example.mediaexplorer.util.inflateChildView

class AudioAdapter : RecyclerView.Adapter<AudioViewHolder>() {

    private val audios = mutableListOf<Audio>()

    var audioCallback: AudioCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(parent.inflateChildView(R.layout.item_audio_content))
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.bind(audios[position], audioCallback!!)
    }

    override fun getItemCount(): Int = audios.size

    fun setData(newData: List<Audio>) {
        val eventDiffUtilCallback = AudioDiffUtilCallback(audios, newData)
        val diffResult = DiffUtil.calculateDiff(eventDiffUtilCallback)
        audios.clear()
        audios.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

}