package com.tomabarbanel.flickerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception
import java.util.ArrayList

private const val TAG = "MainActivity"


class MainActivity : BaseActivity(), GetRawData.OnDownloadDataComplete,
    GetFlikerJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private val flikerRecycleViewAdapter = FlikerRecycleViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")

        //getRawData.setDownloadCompleteListener(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view, this))
        recycler_view.adapter = flikerRecycleViewAdapter


        val url = createUri("android, oreo", "en-us", true)
        val getRawData = GetRawData(this)
        getRawData.execute(url)

        Log.d(TAG, "onCreate ends")
    }

    private fun createUri(searchCreteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, "createUri starts")

        val address = Uri.parse(getString(R.string.DownloadURL))
            .buildUpon()
            .appendQueryParameter("tags", searchCreteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()

        Log.d(TAG, "Address built is ${address}")

        return address
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionItemSelected called")
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    companion object {
//        private val TAG = "MainActivity"
//    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {

        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete, download completed data is")

            val getFlikerJsonData = GetFlikerJsonData(this)
            getFlikerJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete download failed with error $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable called")
        flikerRecycleViewAdapter.loadNewData(data)
        Log.d(TAG, "onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "onError called with ${exception.message}")
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick starts")
        Toast.makeText(this, "normal tap at $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onLongClick start")
        val photo = flikerRecycleViewAdapter.getPhoto(position)

        if(photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }
}
