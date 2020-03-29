package com.tomabarbanel.flickerapp

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.support.*

internal const val FLICKER_QUARR = "FLICK_QUARRY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    internal fun activateToolbar(enableHome: Boolean) {
        Log.d(TAG, "activate toolbar")

        var toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}