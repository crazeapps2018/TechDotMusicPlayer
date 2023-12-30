package com.td.techdotmusicplayer

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.android.musicplayer.di.module.DatabaseModule
import com.td.techdotmusicplayer.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf( DatabaseModule,AppModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}