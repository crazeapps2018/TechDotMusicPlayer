package com.td.techdotmusicplayer.domain.usecase

class GetSongsUseCase(private val playlistRepository: PlaylistRepository) {
    fun getSongs() : List<Song>? {
        return playlistRepository.getSongs()
    }
}