package com.td.techdotmusicplayer.service

import com.google.android.exoplayer2.MediaItem
import java.time.Duration

/**
* To make an interaction between [SongPlayerService] & [BaseSongPlayerActivity]
*
 * @author tech-dharamveer
 * */

interface OnPlayerServiceCallback {
    fun updateSongData(mediaItem: MediaItem)
    fun updateSongProgress(duration: Long, position:Long)
    fun onIsPlayingChanged(isPlaying:Boolean)
    fun updateUiForPlayingMediaItem(mediaItem: MediaItem?)
}