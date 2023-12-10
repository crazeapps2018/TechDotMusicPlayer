package com.td.techdotmusicplayer.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.td.techdotmusicplayer.data.model.Song

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: Song):Long

    @Query("SELECT * FROM Song")
    fun loadAll():MutableList<Song>

    @Delete
    fun delete(song: Song)

    @Query("DELETE FROM song")
    fun deleteAll()

    @Query("SELECT * FROM Song where id = :songId")
    fun loadOneBySongId(songId: Long) : Song?

    @Query("SELECT * FROM Song where title = :songTitle")
    fun loadOneBySongTitle(songTitle:String):Song?

    @Update
    fun update(song: Song)
}