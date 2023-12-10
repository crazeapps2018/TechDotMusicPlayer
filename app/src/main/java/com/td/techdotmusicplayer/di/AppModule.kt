package com.td.techdotmusicplayer.di

import com.td.techdotmusicplayer.di.module.createPlaylistRepository
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository
import com.td.techdotmusicplayer.domain.usecase.DeleteSongUseCase
import com.td.techdotmusicplayer.domain.usecase.GetSongsUseCase
import com.td.techdotmusicplayer.domain.usecase.SaveSongDataUseCase
import com.td.techdotmusicplayer.presentation.playlist.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel{
        PlaylistViewModel(
            saveSongDataUseCase = get(),
            getSongsUseCase = get(),
            deleteSongUseCase = get()
        )
    }
    single { createGetSongsUseCase(get()) }
    single { createDeleteSongUseCase(get()) }
    single { createSaveSongDataUseCase(get()) }
    single { createPlaylistRepository(get()) }
}

fun createSaveSongDataUseCase(
    playlistRepository: PlaylistRepository
): SaveSongDataUseCase {
    return SaveSongDataUseCase(playlistRepository)
}

fun createDeleteSongUseCase(
    playlistRepository: PlaylistRepository
): DeleteSongUseCase {
    return DeleteSongUseCase(playlistRepository)
}

fun createGetSongsUseCase(
    playlistRepository: PlaylistRepository
): GetSongsUseCase {
    return GetSongsUseCase(playlistRepository)
}