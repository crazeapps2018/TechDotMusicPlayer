package com.td.techdotmusicplayer.domain.usecase

import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository

class GetSongsUseCase(private val playlistRepository: PlaylistRepository) {
    fun getSongs() : List<Song>? {
        return playlistRepository.getSongs()
    }
}