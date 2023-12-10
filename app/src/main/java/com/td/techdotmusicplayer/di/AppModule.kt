package com.td.techdotmusicplayer.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel{
        PlaylistViewModel
    }
}