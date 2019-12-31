package com.example.mediaexplorer.util

import androidx.viewpager2.widget.ViewPager2
import com.example.mediaexplorer.Constants.TABS_NAME
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun setupTabsWithViewPager(tabLayout: TabLayout, viewPager2: ViewPager2) {
    TabLayoutMediator(tabLayout, viewPager2,
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = TABS_NAME[position]
        }).attach()
}