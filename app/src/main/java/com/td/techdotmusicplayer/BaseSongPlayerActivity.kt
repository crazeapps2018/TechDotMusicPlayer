package com.td.techdotmusicplayer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.td.techdotmusicplayer.SongPlayerViewModel.Companion.getPlayerViewModelInstance
import com.td.techdotmusicplayer.service.OnPlayerServiceCallback
import com.td.techdotmusicplayer.service.SongPlayerService

open class BaseSongPlayerActivity : AppCompatActivity(), OnPlayerServiceCallback {

    private var mService: SongPlayerService? = null
    private var mBound = false
    private var mMediaItem: MediaItem? = null
    private var mMediaItems: ArrayList<MediaItem>? = null
    private var msg = 0
    val songPlayerViewModel: SongPlayerViewModel = getPlayerViewModelInstance()

    private val mHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg){
                ACTION_PLAY_SONG_IN_LIST -> mService?.play(mMediaItems,mMediaItem)
                ACTION_PAUSE -> mService?.pause()
                ACTION_STOP -> {
                    mService?.stop()
                    songPlayerViewModel.stop()
                }
            }
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            // We've bound to SongPlayerService, cast the IBinder and get SongPlayerService instance
            val binder = service as SongPlayerService.LocalBinder
            mService = binder.service
            mBound = true
            mService?.sub
        }
    }


}