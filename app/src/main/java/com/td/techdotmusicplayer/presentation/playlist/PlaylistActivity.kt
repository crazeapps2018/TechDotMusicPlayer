package com.td.techdotmusicplayer.presentation.playlist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.player.BaseSongPlayerActivity
import com.google.android.material.snackbar.Snackbar
import com.td.techdotmusicplayer.R
import com.td.techdotmusicplayer.data.model.Song
import com.td.techdotmusicplayer.presentation.songplayer.SongPlayerActivity
import com.td.techdotmusicplayer.util.ManagePermissions
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : BaseSongPlayerActivity(), OnPlaylistAdapterListener {

    private var adapter: PlaylistAdapter? = null
    private val viewModel: PlaylistViewModel by viewModel()

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions


    // Initialize a list of required permissions to request runtime
    val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
        )
    } else {
        listOf(

            Manifest.permission.READ_EXTERNAL_STORAGE

        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        setSupportActionBar(toolbar)


        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)

        adapter = PlaylistAdapter(this)
        playlist_recycler_view.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            managePermissions.checkPermissions()



        fab.setOnClickListener { view ->
            openMusicList()
        }

        viewModel.playlistData.observe(this, {
            adapter?.songs = it
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSongsFromDb()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_AUDIO_KEY) {
            data?.data?.let {
                addSong(it)
            }
        }
    }

    private fun addSong(musicData: Uri) {
        val cursor = contentResolver?.query(
            musicData,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
            ), null, null, null
        )
        while (cursor?.moveToNext() == true) {
            val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))

            val cursorAlbums = contentResolver?.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
                MediaStore.Audio.Albums._ID + "=?",
                arrayOf<String>(albumId),
                null
            )
            var albumArt: String? = null
            if (cursorAlbums?.moveToFirst() == true) {
                albumArt =
                    cursorAlbums.getString(cursorAlbums.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
            }

            val song = Song(
                id,
                title.toString(),
                artist,
                path,
                albumArt,
            )
            viewModel.saveSongData(song)
        }
        cursor?.close()
    }


    private fun showRemoveSongItemConfirmDialog(song: Song) {
        // setup the alert builder
        AlertDialog.Builder(this)
            .setMessage("Are you sure to remove this song?")
            // add a button
            .apply {
                setPositiveButton(R.string.yes) { _, _ ->
                    removeMusicFromList(song)
                }
                setNegativeButton(R.string.no) { _, _ ->
                    // User cancelled the dialog
                }
            }
            // create and show the alert dialog
            .show()
    }

    override fun removeSongItem(song: Song) {
        showRemoveSongItemConfirmDialog(song)
    }

    private fun removeMusicFromList(song: Song) {
        songPlayerViewModel.stop()
        viewModel.removeItemFromList(song)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_READ_EXTERNAL_STORAGE_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {// Permission Granted
                    openMusicList()
                } else {
                    // Permission Denied
                    Snackbar.make(
                        playlist_recycler_view,
                        getString(R.string.you_denied_permission),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun openMusicList() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_AUDIO_KEY)
    }

    override fun playSong(song: Song, songs: ArrayList<Song>) {
        SongPlayerActivity.start(this, song, songs)
    }

    companion object {
        const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE_CODE = 7031
        const val PICK_AUDIO_KEY = 2017
    }
}
