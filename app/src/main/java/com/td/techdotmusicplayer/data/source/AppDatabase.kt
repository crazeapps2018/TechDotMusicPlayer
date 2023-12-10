package com.td.techdotmusicplayer.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.data.source.dao.SongDao

/**
 * To manage data items that can be accessed, updated
 * & maintain relationship between them
 * **/
@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract val songDao: SongDao

    companion object {
        const val DB_NAME = "TechDotMusicApp.db"
    }
}