package com.example.topten

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseAplications {
    private val TAG = "ParseApplications"
    val apps = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        Log.d (TAG, "parse calles with ${xmlData}")
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()

            while(eventType != XmlPullParser.END_DOCUMENT) {

            }

        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}