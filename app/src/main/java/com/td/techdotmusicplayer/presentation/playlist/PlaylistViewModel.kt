package com.td.techdotmusicplayer.presentation.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.domain.usecase.DeleteSongUseCase
import com.td.techdotmusicplayer.domain.usecase.GetSongsUseCase
import com.td.techdotmusicplayer.domain.usecase.SaveSongDataUseCase

class PlaylistViewModel(
    private val saveSongDataUseCase: SaveSongDataUseCase,
    private val getSongsUseCase: GetSongsUseCase,
    private val deleteSongUseCase: DeleteSongUseCase
): ViewModel() {

    val playlistData = MutableLiveData<List<Song>>()

    fun saveSongData(song: Song) {
        saveSongDataUseCase.saveSongItem(song)
    }

    fun getSongsFromDb(){
        playlistData.value = getSongsUseCase.getSongs()
    }

    fun removeItemFromList(song: Song){
        deleteSongUseCase.deleteSongItem(song)
        val list = playlistData.value as ArrayList<Song>
        list.remove(song)
        playlistData.value = list
    }

}