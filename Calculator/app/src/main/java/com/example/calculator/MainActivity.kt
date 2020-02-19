package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    private var oprOne: Double? = null
    private var pendingOperation: String = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById((R.id.newNumber))

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val decimalPoint: Button = findViewById(R.id.decimalpoint)

        val plus: Button = findViewById(R.id.plus)
        val minus: Button = findViewById(R.id.minus)
        val multiply: Button = findViewById(R.id.multyply)
        val divide: Button = findViewById(R.id.devide)
        val equalsBtn: Button = findViewById(R.id.equals)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        btn0.setOnClickListener(listener)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
        btn3.setOnClickListener(listener)
        btn4.setOnClickListener(listener)
        btn5.setOnClickListener(listener)
        btn6.setOnClickListener(listener)
        btn7.setOnClickListener(listener)
        btn8.setOnClickListener(listener)
        btn9.setOnClickListener(listener)
        decimalPoint.setOnClickListener(listener)

        val oprListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                preformOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        plus.setOnClickListener(oprListener)
        minus.setOnClickListener(oprListener)
        multiply.setOnClickListener(oprListener)
        divide.setOnClickListener(oprListener)
        equalsBtn.setOnClickListener(oprListener)
    }

    private fun preformOperation(value: Double, operation: String) {
        if (oprOne == null) {
            oprOne = value
        } else if (pendingOperation == "=") {
            pendingOperation = operation
        }

        when (pendingOperation) {
            "=" -> oprOne = value
            "/" -> oprOne = if (value == 0.0) {
                Double.NaN //handle attempt to divide by 0
            } else {
                oprOne!! / value
            }
            "*" -> oprOne = oprOne!! * value
            "-" -> oprOne = oprOne!! - value
            "+" -> oprOne = oprOne!! + value
        }

        result.setText(oprOne.toString())
        newNumber.setText("")
    }
}
