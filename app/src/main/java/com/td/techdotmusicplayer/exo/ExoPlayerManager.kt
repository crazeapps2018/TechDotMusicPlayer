package com.td.techdotmusicplayer.exo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.td.techdotmusicplayer.service.SongPlayerService

/**
 * This class is responsible for managing the player(actions, state, ...) using [ExoPlayer]
 *
 *
 * @author tech-dharamveer
 * **/


class ExoPlayerManager(
    val context : Context,
    private val callback: OnExoPlayerManagerCallback
) : Player.Listener {

    private val mAudioNoisyIntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
    private var mWifiLock : WifiManager.WifiLock? = null
    private var mAudioManager:  AudioManager? = null
    private var mPlayOnFocusGain: Boolean = false
    private var mAudioNoisyReceiverRegistered: Boolean = false
    private var mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private var player: ExoPlayer? = null


    private val mAudioNoisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent.action){
                if (mPlayOnFocusGain || player != null && player?.playWhenReady == true) {
                    val i = Intent(context, SongPlayerService::class.java).apply {
                        action = SongPlayerService.ACTION_CMD
                        putExtra(SongPlayerService.CMD_NAME, SongPlayerService.CMD_PAUSE)
                    }
                    context.applicationContext.startService(i)
                }
            }
        }
    }

    private val mUpdateProgressHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val duration = player?.duration ?: 0
            val position = player?.currentPosition ?: 0
            callback.onUpdateProgress(duration,position)
            sendEmptyMessageDelayed(0,UPDATE_PROGRESS_DELAY)
        }
    }

    //Whether to return STATE_NONE or STATE_STOPPED when mExoPlayer is null
    private var mExoPlayerIsStopped = false
    private val mOnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when(focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> mCurrentAudioFocusState = AUDIO_FOCUSED
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                    // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {}

            }
        }


    companion object {
        const val UPDATE_PROGRESS_DELAY = 500L

        // The volume we set the media player to when we lose audio focus, but are
        // allowed to reduce the volume instead of stopping playback.
        private const val VOLUME_DUCK = 0.2f

        // The volume we set the media player when we have audio focus.
        private const val VOLUME_NORMAL = 1.0f

        // we don't have audio focus, and can't duck (play at a low volume)
        private const val AUDIO_NO_FOCUS_NO_DUCK = 0;

        // we don't have focus, but can duck (play at a low volume)
        private const val AUDIO_NO_FOCUS_CAN_DUCK = 1

        // we have full audio focus
        private const val AUDIO_FOCUSED = 2
    }

}