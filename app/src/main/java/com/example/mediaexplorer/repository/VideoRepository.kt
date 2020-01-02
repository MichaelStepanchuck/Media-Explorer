package com.example.mediaexplorer.repository

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.mediaexplorer.entity.Video
import io.reactivex.Single
import org.threeten.bp.Duration
import java.io.File

class VideoRepository(val context: Context) {

    fun getAllVideos(): Single<MutableList<Video>> {
        val arrayList = arrayListOf<Video>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE
        )
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.apply {
            try {
                moveToFirst()
                do {
                    arrayList.add(
                        Video(
                            getInt(getColumnIndexOrThrow(MediaStore.Video.Media._ID)),
                            getString(getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)),
                            getString(getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),
                            getVideoDuration(
                                context,
                                getString(getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                            ),
                            getLong(getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                        )
                    )
                } while (moveToNext())
                close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return Single.just(arrayList)
    }

    private fun getVideoDuration(context: Context, videoPath: String): Duration {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.fromFile(File(videoPath)))
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()

        return Duration.ofMillis(time.toLong())
    }

}