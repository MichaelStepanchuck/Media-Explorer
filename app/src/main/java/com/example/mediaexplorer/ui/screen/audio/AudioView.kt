package com.example.mediaexplorer.ui.screen.audio

import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AudioView : MvpView {
    fun setAdapter(audioAdapter: AudioAdapter)
    fun playAudioWithPlayer(videoUri: Uri)
}