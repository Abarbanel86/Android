package com.tomabarbanel.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_stand_alone.*
import java.lang.IllegalArgumentException

class StandAloneActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stand_alone)
        btnPlayVideo.setOnClickListener(this)
        btnPlaylist.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = when(view?.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(this, getString(R.string.api_key), YOUTUBE_VIDEO_ID, 0, true, false)

            R.id.btnPlaySingle -> YouTubeStandalonePlayer.createPlaylistIntent(this, getString(R.string.api_key), YOUTUBE_PLAYLIST, 0, 0, true, true)

            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}