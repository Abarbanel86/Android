package com.tomabarbanel.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "0L4C968acas"
const val YOUTUBE_PLAYLIST = "PLt4dvC3zSbYCx31m9CKFqXlqzdlNieLoM"

class YouTubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

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
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubInitRes: YouTubeInitializationResult?
    ) {
        val REQUESR_CODE = 0

        if(youtubInitRes?.isUserRecoverableError == true) {
            youtubInitRes.getErrorDialog(this, REQUESR_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the player ${youtubInitRes}"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
