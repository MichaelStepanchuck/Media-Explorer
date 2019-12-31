package com.example.mediaexplorer.ui.screen.audio

import androidx.recyclerview.widget.DiffUtil
import com.example.mediaexplorer.entity.Audio
import com.example.mediaexplorer.entity.Video

class AudioDiffUtilCallback(private val oldList: List<Audio>, private val newList: List<Audio>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldList[oldItemPosition]
        val newEvent = newList[newItemPosition]

        return oldEvent.path == newEvent.path
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldList[oldItemPosition]
        val newEvent = newList[newItemPosition]

        return oldEvent == newEvent
    }

}