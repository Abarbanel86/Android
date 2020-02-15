package com.example.buttonclickapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
private val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var  textView:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInput: EditText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        textView?.text = ""
        textView?.movementMethod = ScrollingMovementMethod()
        userInput.text.clear()

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View) {
                Log.d(TAG, "onClick Called")
                textView?.append(userInput.text)
                textView?.append("\n")
                userInput.setText("")
            }
        })
    }

    override fun onStart() {
        Log.d(TAG, "onStart Called")
        super.onStart()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onRestoreState Called")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        Log.d(TAG, "onResume Called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause Called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop Called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy Called")
        super.onDestroy()
    }
}
