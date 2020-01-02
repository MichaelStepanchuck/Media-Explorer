package com.example.mediaexplorer.repository

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.mediaexplorer.entity.Audio
import io.reactivex.Single
import org.threeten.bp.Duration
import java.io.File

class AudioRepository(val context: Context) {

    fun getAllAudios(): Single<MutableList<Audio>> {
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE
        )

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )!!

        val songs: MutableList<Audio> = ArrayList()
        cursor.apply {
            while (cursor.moveToNext()) {
                songs.add(
                    Audio(
                        getInt(getColumnIndexOrThrow(MediaStore.Audio.Media._ID)),
                        getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                        getString(getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                        getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)),
                        getAudioDuration(
                            context,
                            getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        ),
                        getLong(getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                    )
                )
            }
        }
        cursor.close()

        return Single.just(songs)
    }

    private fun getAudioDuration(context: Context, videoPath: String): Duration {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.fromFile(File(videoPath)))
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()

        return Duration.ofMillis(time.toLong())
    }

}