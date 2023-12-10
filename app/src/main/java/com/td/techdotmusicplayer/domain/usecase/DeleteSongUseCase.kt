package com.td.techdotmusicplayer.domain.usecase

class DeleteSongUseCase(private val playlistRepository: PlaylistRepository) {
    fun deleteSongItem(song: Song){
        playlistRepository.delete(song)
    }
}