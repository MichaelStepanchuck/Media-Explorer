package com.example.mediaexplorer.repository

import android.content.ContentResolver
import android.provider.MediaStore.MediaColumns.*
import android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
import com.example.mediaexplorer.entity.Video
import io.reactivex.Single
import org.threeten.bp.Duration

class VideoRepository(private val contentResolver: ContentResolver) {

    fun getAllVideos(): Single<MutableList<Video>> {
        val arrayList = arrayListOf<Video>()
        val projection = arrayOf(_ID, DATA, DISPLAY_NAME, SIZE, DURATION)

        contentResolver.query(EXTERNAL_CONTENT_URI, projection, null, null, null)?.apply {
            while (moveToNext()) {
                arrayList.add(
                    Video(
                        getInt(getColumnIndexOrThrow(_ID)),
                        getString(getColumnIndexOrThrow(DISPLAY_NAME)),
                        getString(getColumnIndexOrThrow(DATA)),
                        Duration.ofMillis(getLong(getColumnIndexOrThrow(DURATION))),
                        getLong(getColumnIndexOrThrow(SIZE))
                    )
                )
            }

            close()
        }

        return Single.just(arrayList)
    }

}