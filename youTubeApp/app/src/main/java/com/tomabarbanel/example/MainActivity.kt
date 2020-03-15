package com.tomabarbanel.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlaySingle.setOnClickListener(this)
        btnStandAlone.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = when(v.id) {
            R.id.btnPlaySingle -> Intent(this, YouTubeActivity::class.java)
            R.id.btnStandAlone -> Intent(this, StandAloneActivity::class.java)

            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}
