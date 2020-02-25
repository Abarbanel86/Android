package com.example.topten

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")
        val downloadData = DownloadData()
        downloadData.execute(url)
        Log.d(TAG,"onCreate - done")
    }

    companion object {
        private class DownloadData: AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackGround starts with ${url[0]}")

                val rssFeed = downloadXML(url[0])
                if(rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: download failed")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                val xmlResult = StringBuilder()

                try {
                    val url = URL(urlPath)
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(TAG, "downloadXML: The response code was $response")

//                    val inputSteam = connection.inputStream
//                    val inputStreamReader = InputStreamReader(inputSteam)
//                    val reader = BufferedReader(inputStreamReader)
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))

                    val inputBuffer = CharArray(500)
                    var charsRead = 0
                    while(charsRead >= 0) {
                        charsRead = reader.read(inputBuffer)
                        if(charsRead > 0) {
                            xmlResult.append((String(inputBuffer, 0, charsRead)))
                        }
                    }

                    reader.close()
                    Log.d(TAG, "recived ${xmlResult.length} bytes")
                    return xmlResult.toString()
                } catch(e: MalformedURLException) {
                    Log.e(TAG, "downloadXML: Invalid URL ${e.message}")
                } catch(e: IOException) {
                    Log.e(TAG, "downloadXML: IO exception ${e.message}")
                } catch (e: Exception) {
                    Log.e(TAG, "downloadXML: Exception ${e.message}")
                }

                return "" // issue encountered returns empty string
            }
        }
    }
}
