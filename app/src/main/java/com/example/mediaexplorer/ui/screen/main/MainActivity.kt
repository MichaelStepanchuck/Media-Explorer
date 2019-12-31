package com.example.mediaexplorer.ui.screen.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.mediaexplorer.Constants.REQUIRED_PERMISSIONS
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = MainPresenter()

    private val viewPagerAdapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (mainPresenter.permissionsIsGranted(this)) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        } else {
            setupUI()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val videoUri = data?.data

        if (requestCode == VIDEO_CAPTURE) {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupUI()
            } else {
                showToast("Приложению нужен доступ к памяти")
            }
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                addMediaContentFAB.hide()
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                addMediaContentFAB.show()
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            addMediaContentFAB.apply {
                hide()
                if (position == 0) {
                    setOnClickListener(onRecordVideoClickListener)
                    icon = getDrawable(R.drawable.ic_video_call_24px)
                    text = "Record video"
                } else {
                    setOnClickListener(onRecordAudioClickListener)
                    icon = getDrawable(R.drawable.ic_volume_up_24px)
                    text = "Record audio"
                }
                show()
            }
        }
    }

    override fun setupAdapter(viewPagerAdapter: ViewPagerAdapter) {
        mediaFragmentsViewPager.adapter = viewPagerAdapter
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setupUI() {
        mediaFragmentsViewPager.adapter = viewPagerAdapter
        mediaFragmentsViewPager.registerOnPageChangeCallback(onPageChangeCallback)
        TabLayoutMediator(tabs, mediaFragmentsViewPager,
            TabConfigurationStrategy { tab, position ->
                tab.text = "Tab " + (position + 1)
            }).attach()
        mainPresenter.setupMediaRecorder()
    }

    private val onRecordVideoClickListener = View.OnClickListener {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO_CAPTURE)
    }

    private val onRecordAudioClickListener = View.OnClickListener {
        mainPresenter.notifyRecordAudioButtonClick()
    }

    companion object {
        private const val VIDEO_CAPTURE = 101
        private const val PERMISSIONS_REQUEST_CODE = 100
    }

}
