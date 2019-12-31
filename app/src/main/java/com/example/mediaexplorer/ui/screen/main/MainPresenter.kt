package com.example.mediaexplorer.ui.screen.main

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.media.MediaRecorder
import android.os.Environment
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import java.io.File
import java.io.IOException

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false

    fun notifyRecordAudioButtonClick() {
        if (state) {
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
            state = true
            viewState.showToast("Recording started!")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            viewState.showToast("Recording saved!")
        }
    }

    fun permissionsIsGranted(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, RECORD_AUDIO) != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED
    }


}