package com.example.mediaexplorer.ui.screen.video

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mediaexplorer.entity.Video
import com.example.mediaexplorer.repository.VideoRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class VideoPresenter(private val context: Context) : MvpPresenter<VideoView>() {

    private val videoAdapter = VideoAdapter()

    private val videoRepository = VideoRepository()

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        videoAdapter.videoCallback = videoCallback
        viewState.setAdapter(videoAdapter)
        subscribeForVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun subscribeForVideos() {
        val disposable = Observable.interval(UPDATE_INTERVAL_IN_SECONDS, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getAllVideos() }
            .subscribe { getAllVideos() }

        compositeDisposable.add(disposable)
    }

    private fun getAllVideos() {
        val disposable = videoRepository.getAllVideos(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { videoAdapter.setData(it) },
                { Timber.e("Failed to subscribe on video list") }
            )

        compositeDisposable.add(disposable)
    }

    private val videoCallback = object : VideoCallback {
        override fun onVideoListItemClick(video: Video) {
            context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(
                    Uri.parse(video.path),
                    "video/*"
                )
            })
        }
    }

    companion object {
        const val UPDATE_INTERVAL_IN_SECONDS = 15L
    }

}