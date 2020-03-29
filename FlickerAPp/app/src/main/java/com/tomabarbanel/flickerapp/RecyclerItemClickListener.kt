package com.tomabarbanel.flickerapp

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView


class RecyclerItemClickListener (context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener(){

    private val TAG = "RecyclerClickListener"
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onSingleTapUp calling listener")
            if (childView != null) {
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onLongPress calling listener")
            if (childView != null) {
                listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
        }
    })

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: starts $e")
        val res = gestureDetector.onTouchEvent(e)
//        return super.onInterceptTouchEvent(rv, e)
        return true
    }
}