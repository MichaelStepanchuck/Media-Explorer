package com.example.mediaexplorer.ui.screen.main

import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.mediaexplorer.ui.ViewPagerAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun setupAdapter(viewPagerAdapter: ViewPagerAdapter)
    fun showToast(text: String)
    fun setFABText(textId: Int)
    fun setFABIcon(drawableId: Int)
    fun setFABClickListener(onClickListener: View.OnClickListener)
    fun showFAB()
    fun hideFAB()
    fun startCameraIntent()
}