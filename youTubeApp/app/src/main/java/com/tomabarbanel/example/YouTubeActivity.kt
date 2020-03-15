package com.tomabarbanel.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "0L4C968acas"
const val YOUTUBE_PLAYLIST = "PLt4dvC3zSbYCx31m9CKFqXlqzdlNieLoM"

class YouTubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "youtubeActivity"
    private val DIALOG_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_you_tube)
//        val layout= findViewById<ConstraintLayout>(R.id.activity_youtube)
        val layout = layoutInflater.inflate(R.layout.activity_you_tube, null) as ConstraintLayout
        setContentView(layout)

//        val btn1 = Button(this)
//        btn1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        btn1.text = "Button added"
//        layout.addView(btn1)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT

        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.api_key), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess player is ${player?.javaClass}")
        Toast.makeText(this, "Initialization of play success", Toast.LENGTH_SHORT).show()

        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            player?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            player?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubInitRes: YouTubeInitializationResult?
    ) {

        if (youtubInitRes?.isUserRecoverableError == true) {
            youtubInitRes.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the player ${youtubInitRes}"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YouTubeActivity, "Ad has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {}

        override fun onVideoStarted() {
            Toast.makeText(this@YouTubeActivity, "Ad has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@YouTubeActivity, "Ad has ended", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {}
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {}

        override fun onBuffering(p0: Boolean) {}

        override fun onPlaying() {
            Toast.makeText(this@YouTubeActivity, "video is playing", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YouTubeActivity, "video has Stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YouTubeActivity, "video is paused", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with $requestCode and result code $resultCode")

        if(requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
        }
    }
}
