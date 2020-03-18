package com.tomabarbanel.flickerapp

class Photo (val title: String, val auther: String, val autherId: String, val link: String, val tags: String, val image: String){

    override fun toString(): String {
        return "Photo(title='$title', auther='$auther', autherId='$autherId', link='$link', tags='$tags', image='$image')"
    }
}