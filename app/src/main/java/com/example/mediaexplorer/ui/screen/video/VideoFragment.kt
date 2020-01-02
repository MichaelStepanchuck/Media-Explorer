package com.example.mediaexplorer.ui.screen.video

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_video.*
import org.koin.android.ext.android.get

class VideoFragment : BaseFragment(), VideoView {

    @InjectPresenter
    lateinit var videoPresenter: VideoPresenter

    @ProvidePresenter
    fun provideVideoPresenter() = get<VideoPresenter>()

    override fun getLayoutResId(): Int = R.layout.fragment_video

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun setAdapter(videoAdapter: VideoAdapter) {
        videoListRecyclerView.adapter = videoAdapter
    }

    override fun playVideoWithPlayer(videoUri: Uri) {
        startActivity(Intent(ACTION_VIEW).apply {
            setDataAndType(
                videoUri,
                "video/*"
            )
        })
    }

}
