package com.tomabarbanel.flickerapp

import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class GetFlikerJsonData(private val listener: MediaPlayer.OnTimedMetaDataAvailableListener) :
    AsyncTask<String, Void, ArrayList<Photo>>() {
    private val TAG = "GetFlikerJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackGroundStarts")

        try{
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")

            for(i in 0 until itemsArray.length()) {
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")

                val jsonMedia = jsonPhoto.getJSONObject("media")
                val url = jsonMedia.getString("m")
                val link = url.replaceFirst("_m.jpg", "_b.jpg")
            }
        } catch (e: Exception) {

        }
        
    }

    override fun onPostExecute(result: ArrayList<Photo>?) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
    }
}