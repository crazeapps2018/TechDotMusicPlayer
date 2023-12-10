package com.td.techdotmusicplayer.data.respository

import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.data.source.AppDatabase
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository

class PlaylistRepositoryImp(private val appDatabase: AppDatabase) : PlaylistRepository {
    override fun saveSongData(song: Song): Long {
        return appDatabase.songDao.insert(song)
    }

    override fun getSongs(): List<Song>? {
        return appDatabase.songDao.loadAll()
    }

    override fun delete(song: Song) {
        appDatabase.songDao.delete(song)
    }


}