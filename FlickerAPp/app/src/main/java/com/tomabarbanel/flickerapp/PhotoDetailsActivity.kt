package com.tomabarbanel.flickerapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        activateToolbar(true)

        val photo = intent.getParcelableExtra(PHOTO_TRANSFER) as Photo
        photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
        photo_author.text = photo.auther
        photo_tags.text = resources.getString(R.string.photo_title_tags, photo.tags)

        Picasso.get()
            .load(photo.link)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(photo_image)
    }

}
