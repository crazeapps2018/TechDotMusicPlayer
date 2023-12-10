package com.td.techdotmusicplayer.domain.usecase

import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository

class DeleteSongUseCase(private val playlistRepository: PlaylistRepository) {
    fun deleteSongItem(song: Song){
        playlistRepository.delete(song)
    }
}