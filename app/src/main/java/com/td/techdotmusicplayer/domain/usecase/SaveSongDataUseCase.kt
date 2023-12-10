package com.td.techdotmusicplayer.domain.usecase

class SaveSongDataUseCase(private val playlistRepository: PlaylistRepository) {
    fun saveSongItem(song: Song){
        playlistRepository.saveSongData(song)
    }
}