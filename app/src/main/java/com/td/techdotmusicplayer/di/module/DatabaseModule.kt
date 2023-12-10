package com.td.techdotmusicplayer.di.module

import android.app.Application
import androidx.room.Room
import com.td.techdotmusicplayer.data.respository.PlaylistRepositoryImp
import com.td.techdotmusicplayer.data.source.AppDatabase
import com.td.techdotmusicplayer.data.source.dao.SongDao
import com.td.techdotmusicplayer.domain.respository.PlaylistRepository
import org.koin.dsl.module

val DatabaseModule = module {
    single { createAppDatabase(get()) }
    single { createSongDao(get()) }
    single { createPlaylistRepository(get()) }
}

internal fun createAppDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    )
        //.fallbackToDestructiveMigration() // allows database to be cleared after upgrading version
        .allowMainThreadQueries()
        .build()
}

fun createSongDao(appDatabase: AppDatabase):SongDao {
    return  appDatabase.songDao
}

fun createPlaylistRepository(appDatabase: AppDatabase):PlaylistRepository{
    return PlaylistRepositoryImp(appDatabase)
}