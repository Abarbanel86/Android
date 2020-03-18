package com.tomabarbanel.flickerapp

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

private const val TAG = "GetRawData"

class GetRawData(private val listener: OnDownloadDataComplete) : AsyncTask<String, Void, String>() {
    private var status = DownloadStatus.IDLE

    interface  OnDownloadDataComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }
//    fun setDownloadCompleteListener(callBackObject: MainActivity) {
//        listener = callBackObject
//    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            status = DownloadStatus.NOT_INITIALIZED

            return "URL string empty"
        }

        try {
            status = DownloadStatus.OK

            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    status = DownloadStatus.NOT_INITIALIZED
                    "doInBackGround: invalid URL ${e.message}"
                }
                is IOException -> {
                    status = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackGround: IO Exception reading data ${e.message}"
                }
                is SecurityException -> {
                    status = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackGround: Security exception ${e.message}"
                }
                else -> {
                    status = DownloadStatus.ERROR
                    "doInBackground unknown error ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)

            return errorMessage
        }
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "omPostExecute called ${result}")
        listener.onDownloadComplete(result, status)
    }
}