package com.example.buttonclickapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var  textView:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
                textView?.append(userInput.text)
                textView?.append("\n")
                userInput.setText("")
            }
        })
    }
}
