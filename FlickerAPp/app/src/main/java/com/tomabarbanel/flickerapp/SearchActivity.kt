package com.tomabarbanel.flickerapp

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.widget.SearchView

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        activateToolbar(true)
        Log.d(TAG, "onCreate: ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu starts")

        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        Log.d(TAG, "onCreateOptionsMenu $componentName")
        Log.d(TAG, "onCreateOptionsMenu hint is ${searchView?.queryHint}")
        Log.d(TAG, "onCreateOptionsMenu info is $searchableInfo")

        searchView?.isIconified = false

        Log.d(TAG, "onCreateOptionsMenu finishing")

        return true
    }
}
