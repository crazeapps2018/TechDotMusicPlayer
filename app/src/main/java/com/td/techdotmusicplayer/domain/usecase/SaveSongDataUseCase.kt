package com.td.techdotmusicplayer.domain.usecase

import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository

class SaveSongDataUseCase(private val playlistRepository: PlaylistRepository) {
    fun saveSongItem(song: Song){
        playlistRepository.saveSongData(song)
    }
}