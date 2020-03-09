package com.example.topten

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalStateException
import java.net.URL
import kotlin.properties.Delegates


class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            summary = $summary
            imageURL = $imageURL
        """.trimIndent()
    }
}

private const val FEED_COUNT = "FeedCount"
private const val FEED_TYPE = "FeedType"

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var url =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private var downloadData: DownloadData? = null
    private var feedLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        downloadUrl(url.format(feedLimit))
    }

    private fun downloadUrl(url: String) {
        Log.d(TAG, "downloadUrl started")
        downloadData = DownloadData(this, xmlListView)
        downloadData?.execute(url)
        Log.d(TAG, "downloadUrl - done")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if(feedLimit == 10) {
            menu?.findItem(R.id.mnu10)?.isChecked = true
        } else {
            menu?.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var urlPath: String = url

        when(item.itemId) {
            R.id.mnuRefresh ->
                downloadUrl(urlPath.format(feedLimit))
            R.id.mnuFree ->
                urlPath = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            R.id.mnuPaid ->
                urlPath = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            R.id.mnuSongs ->
                urlPath = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            R.id.mnu10, R.id.mnu25 -> {
                if(!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                    Log.d(TAG, "onOptionsSelected: ${item.title} setting feed limit to $feedLimit")
                } else {
                    Log.d(TAG, "onOptionsSelected: ${item.title} setting feed limit unchanged")
                    return true
                }
            }
            else ->
                return super.onOptionsItemSelected(item)
        }

        if(urlPath != url){
            downloadUrl(urlPath.format(feedLimit))
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView : ListView) : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"
            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val pasrseApplications = ParseAplications()
                pasrseApplications.parse(result)

//                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, pasrseApplications.apps)
//                propListView.adapter = arrayAdapter

                val feedAdapter = FeedAdapter(propContext, R.layout.list_record, pasrseApplications.apps)
                propListView.adapter = feedAdapter
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackGround starts with ${url[0]}")

                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: download failed")
                }
                Log.d(TAG, "Finishing doInBackGround")
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                Log.d(TAG, "Starting downloadURL")
                return URL(urlPath).readText()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(FEED_TYPE, url)
        outState.putInt(FEED_COUNT, feedLimit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        url = savedInstanceState.getString(FEED_TYPE, url)
        feedLimit = savedInstanceState.getInt(FEED_COUNT, 10)
    }
}
