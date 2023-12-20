package com.td.techdotmusicplayer.presentation.playlist

import com.td.techdotmusicplayer.data.model.Song

/**
 * To make an interaction between [PlaylistActivity] & [PlaylistAdapter]
 *
 * @author TechDharamveer
 * **/
interface OnPlaylistAdapterListener {
    fun playSong(song: Song,songs:ArrayList<Song>)
    fun removeSongItem(song: Song)
}