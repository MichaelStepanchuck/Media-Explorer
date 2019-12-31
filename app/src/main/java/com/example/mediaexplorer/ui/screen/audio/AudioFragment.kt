package com.example.mediaexplorer.ui.screen.audio

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.screen.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_audio.*

class AudioFragment : BaseFragment(), AudioView {

    @InjectPresenter
    lateinit var audioPresenter: AudioPresenter

    @ProvidePresenter
    fun provideAudioPresenter() = AudioPresenter(context!!.applicationContext)

    override fun getLayoutResId(): Int = R.layout.fragment_audio

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun setAdapter(audioAdapter: AudioAdapter) {
        audioListRecyclerView.adapter = audioAdapter
    }

}
