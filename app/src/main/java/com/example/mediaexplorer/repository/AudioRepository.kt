package com.example.mediaexplorer.repository

import android.content.ContentResolver
import android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.Audio.Playlists.Members.*
import com.example.mediaexplorer.entity.Audio
import io.reactivex.Single
import org.threeten.bp.Duration

class AudioRepository(private val contentResolver: ContentResolver) {

    fun getAllAudios(): Single<MutableList<Audio>> {
        val songs: MutableList<Audio> = ArrayList()
        val projection = arrayOf(_ID, ARTIST, TITLE, DATA, SIZE, DURATION)

        contentResolver.query(EXTERNAL_CONTENT_URI, projection, null, null, null)?.apply {
            while (moveToNext()) {
                songs.add(
                    Audio(
                        getInt(getColumnIndexOrThrow(_ID)),
                        getString(getColumnIndexOrThrow(ARTIST)),
                        getString(getColumnIndexOrThrow(TITLE)),
                        getString(getColumnIndexOrThrow(DATA)),
                        Duration.ofMillis(getLong(getColumnIndexOrThrow(DURATION))),
                        getLong(getColumnIndexOrThrow(SIZE))
                    )
                )
            }

            close()
        }

        return Single.just(songs)
    }

}