package com.example.mediaexplorer.ui.screen.audio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mediaexplorer.Constants.APPLICATION_TAG
import com.example.mediaexplorer.entity.Audio
import com.example.mediaexplorer.repository.AudioRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@InjectViewState
class AudioPresenter(private val context: Context) : MvpPresenter<AudioView>() {

    private val audioAdapter = AudioAdapter()

    private val audioRepository = AudioRepository()

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        audioAdapter.audioCallback = audioCallback
        viewState.setAdapter(audioAdapter)
        subscribeForAudios()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun subscribeForAudios() {
        val disposable = Observable.interval(15, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getAllAudios() }
            .subscribe { getAllAudios() }

        compositeDisposable.add(disposable)
    }

    private fun getAllAudios() {
        val disposable = audioRepository.getAllAudios(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { audioAdapter.setData(it) },
                { Log.e(APPLICATION_TAG, "Failed to subscribe on audio list") }
            )

        compositeDisposable.add(disposable)
    }

    private val audioCallback = object : AudioCallback {
        override fun onAudioListItemClick(audio: Audio) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.parse(audio.path), "video/*")
            context.startActivity(intent)
        }
    }

}