package com.tomabarbanel.flickerapp

import android.os.Bundle
import android.util.Log

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        activateToolbar(true)
        Log.d(TAG, "onCreate: ends")
    }

}
