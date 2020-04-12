package com.tomabarbanel.flickerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class FlickerImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnailUri: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class FlikerRecycleViewAdapter(private var photoList: List<Photo>): RecyclerView.Adapter<FlickerImageViewHolder>() {
    private val TAG = "FlikerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickerImageViewHolder {
        //called by the layout when it need new view
//        Log.d(TAG, "onBindViewHolder called")
        var view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)

        return FlickerImageViewHolder(view)
    }

    override fun getItemCount(): Int {
//        Log.d(TAG, "getItemCount called")

        return if(photoList.isNotEmpty()) photoList.size else 1
    }

    fun getPhoto(position: Int): Photo? {
        return if(photoList.isNotEmpty()) photoList[position] else null
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FlickerImageViewHolder, position: Int) {
        //called by layout manager when it needs new view
        if(photoList.isEmpty()) {
            holder.thumbnailUri.setImageResource(R.drawable.placeholder)
            holder.title.setText(R.string.no_photos_found)
        } else {
            val photoItem = photoList[position]

//        Log.d(TAG, "onBindViewHolder: ${photoItem.title} -> ${position}")
            Picasso.get()
                .load(photoItem.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnailUri)

            holder.title.text = photoItem.title
        }
    }
}