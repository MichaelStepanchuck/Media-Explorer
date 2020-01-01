package com.example.mediaexplorer.ui.screen.main

import android.media.MediaRecorder
import android.os.Environment
import android.os.SystemClock
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mediaexplorer.R
import java.io.File
import java.io.IOException

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    private var recordingStartTime = 0L

    private fun notifyRecordAudioButtonClick() {
        if (isRecording) {
            stopRecording()
            return
        }

        startRecording()
    }

    private fun setupMediaRecorder() {
        val outputFileName = getVideoFilePath()
        File(outputFileName).createNewFile()
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
            setOutputFile(outputFileName)
        }
    }

    fun notifyPageScrollStateChanged(state: Int) {
        if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
            viewState.hideFAB()
        } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
            viewState.showFAB()
        }
    }

    private fun getVideoFilePath(): String {
        return Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".mp3"
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
            setupMediaRecorder()
            mediaRecorder?.apply {
                prepare()
                start()
            }
            recordingStartTime = SystemClock.uptimeMillis()
            isRecording = true
            viewState.showToast("Recording started!")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (SystemClock.uptimeMillis() - recordingStartTime < 1000) return

        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            recordingStartTime = 0L
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