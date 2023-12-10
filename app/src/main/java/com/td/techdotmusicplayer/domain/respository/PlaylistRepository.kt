package com.td.techdotmusicplayer.domain.respository

import com.td.techdotmusicplayer.data.model.Song

interface PlaylistRepository {

    fun saveSongData(song: Song) : Long
    fun getSongs():List<Song>?
    fun delete(song: Song)
}