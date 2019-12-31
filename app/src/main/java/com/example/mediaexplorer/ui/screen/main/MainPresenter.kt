package com.example.mediaexplorer.ui.screen.main

import android.media.MediaRecorder
import android.os.Environment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mediaexplorer.R
import java.io.File
import java.io.IOException

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    private fun notifyRecordAudioButtonClick() {
        if (isRecording) {
            stopRecording()
            return
        }

        startRecording()
    }

    fun setupMediaRecorder() {
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
        File(output!!).createNewFile()
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
        mediaRecorder?.setOutputFile(output)
    }

    fun notifyPageScrollStateChanged(state: Int) {
        if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
            viewState.hideFAB()
        } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
            viewState.showFAB()
        }
    }

    fun notifyPageSelected(position: Int) {
        viewState.apply {
            hideFAB()
            if (position == 0) {
                setFABClickListener(onRecordVideoClickListener)
                setFABIcon(R.drawable.ic_video_call_24px)
                setFABText(R.string.record_video)
            } else {
                setFABClickListener(onRecordAudioClickListener)
                setFABIcon(R.drawable.ic_volume_up_24px)
                setFABText(R.string.record_audio)
            }
            showFAB()
        }
    }

    private fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording = true
            viewState.showToast("Recording started!")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            isRecording = false
            viewState.showToast("Recording saved!")
        }
    }

    private val onRecordVideoClickListener = View.OnClickListener {
        viewState.startCameraIntent()
    }

    private val onRecordAudioClickListener = View.OnClickListener {
        notifyRecordAudioButtonClick()
    }

}