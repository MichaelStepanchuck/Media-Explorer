package com.example.mediaexplorer.ui.screen.video

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface VideoView : MvpView {
    fun setAdapter(videoAdapter: VideoAdapter)
}