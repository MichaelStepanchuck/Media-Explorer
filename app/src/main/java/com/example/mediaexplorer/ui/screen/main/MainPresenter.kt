package com.example.mediaexplorer.ui.screen.main

import android.media.MediaRecorder
import android.os.Environment
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import java.io.File
import java.io.IOException

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    fun notifyRecordAudioButtonClick() {
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

}