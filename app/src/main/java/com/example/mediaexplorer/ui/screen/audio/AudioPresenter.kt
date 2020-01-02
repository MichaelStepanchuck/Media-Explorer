package com.example.mediaexplorer.ui.screen.audio

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mediaexplorer.entity.Audio
import com.example.mediaexplorer.repository.AudioRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class AudioPresenter(private val audioRepository: AudioRepository) : MvpPresenter<AudioView>() {

    private val audioAdapter = AudioAdapter()

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
        val disposable = Observable.interval(UPDATE_INTERVAL_IN_SECONDS, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getAllAudios() }
            .subscribe { getAllAudios() }

        compositeDisposable.add(disposable)
    }

    private fun getAllAudios() {
        val disposable = audioRepository.getAllAudios()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { audioAdapter.setData(it) },
                { Timber.e("Failed to subscribe on audio list") }
            )

        compositeDisposable.add(disposable)
    }

    private val audioCallback = object : AudioCallback {
        override fun onAudioListItemClick(audio: Audio) {
            viewState.playAudioWithPlayer(Uri.parse(audio.path))
        }
    }

    companion object {
        const val UPDATE_INTERVAL_IN_SECONDS = 15L
    }

}