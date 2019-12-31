package com.example.mediaexplorer.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediaexplorer.ui.screen.audio.AudioFragment
import com.example.mediaexplorer.ui.screen.video.VideoFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = VIEW_PAGER_TABS_COUNT

    override fun createFragment(position: Int): Fragment = if (position == 0) VideoFragment() else AudioFragment()

    companion object {
        private const val VIEW_PAGER_TABS_COUNT = 2
    }

}