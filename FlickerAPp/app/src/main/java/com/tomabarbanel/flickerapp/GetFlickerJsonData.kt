package com.tomabarbanel.flickerapp

import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class GetFlikerJsonData(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<Photo>>() {
    private val TAG = "GetFlikerJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackGroundStarts")

        val photoList = ArrayList<Photo>()
        //Log.d(TAG, params[0])

        try {
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
                val photoUrl = url.replaceFirst("_m.jpg", "_b.jpg")

                val photo = Photo(title, author, authorId, url, tags, photoUrl)
                Log.d(TAG, "doInBackground $photo")
                photoList.add(photo)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d(TAG, "doInBackground: Error processing json data ${e.message}")
            cancel(true)
            listener.onError(e)
        }

        Log.d(TAG, "doInBackGround finished")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute ends")
    }
}