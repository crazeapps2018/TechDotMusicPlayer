package com.td.techdotmusicplayer.exo

import com.google.android.exoplayer2.MediaItem
import java.time.Duration

/**
 * To make an interaction between [ExoPlayerManager] & [SongPlayerService]
 *
 * which returns the result from [ExoPlayerManager]
 *
 * @author Tech-dharamveer
 * **/

interface OnExoPlayerManagerCallback {
    fun onIsPlayingChanged(isPlaying:Boolean)
    fun onUpdateProgress(duration: Long, position: Long)
    fun updateUiForPlayingMediaItem(mediaItem: MediaItem?)
}