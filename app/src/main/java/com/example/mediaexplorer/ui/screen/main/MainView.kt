package com.example.mediaexplorer.ui.screen.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.mediaexplorer.ui.ViewPagerAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun setupAdapter(viewPagerAdapter: ViewPagerAdapter)
    fun showToast(text: String)
}