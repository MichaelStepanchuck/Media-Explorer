package com.example.mediaexplorer.ui.screen.main

import android.Manifest.permission.*
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.ViewPagerAdapter
import com.example.mediaexplorer.util.setupTabsWithViewPager
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = MainPresenter()

    private val viewPagerAdapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUIWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            mainPresenter.notifyPageScrollStateChanged(state)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mainPresenter.notifyPageSelected(position)
        }
    }

    override fun setupAdapter(viewPagerAdapter: ViewPagerAdapter) {
        mediaFragmentsViewPager.adapter = viewPagerAdapter
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun setFABText(textId: Int) {
        addMediaContentFAB.text = getString(textId)
    }

    override fun setFABIcon(drawableId: Int) {
        addMediaContentFAB.icon = getDrawable(drawableId)
    }

    override fun setFABClickListener(onClickListener: View.OnClickListener) {
        addMediaContentFAB.setOnClickListener(onClickListener)
    }

    override fun showFAB() {
        addMediaContentFAB.show()
    }

    override fun hideFAB() {
        addMediaContentFAB.hide()
    }

    override fun startCameraIntent() {
        startActivity(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
    }

    @NeedsPermission(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
    fun setupUI() {
        mediaFragmentsViewPager.adapter = viewPagerAdapter
        mediaFragmentsViewPager.registerOnPageChangeCallback(onPageChangeCallback)
        setupTabsWithViewPager(tabs, mediaFragmentsViewPager)
    }

}
