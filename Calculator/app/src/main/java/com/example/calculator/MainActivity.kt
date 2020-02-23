package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPRAND_ONE = "OprOne"
private const val STATE_OPR1_STORED = "OperandOneStored"

class MainActivity : AppCompatActivity() {
//    ways to initialize in Java
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    private var oprOne: Double? = null
    private var pendingOperation: String = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById((R.id.newNumber))
//
//        val btn0: Button = findViewById(R.id.btn0)
//        val btn1: Button = findViewById(R.id.btn1)
//        val btn2: Button = findViewById(R.id.btn2)
//        val btn3: Button = findViewById(R.id.btn3)
//        val btn4: Button = findViewById(R.id.btn4)
//        val btn5: Button = findViewById(R.id.btn5)
//        val btn6: Button = findViewById(R.id.btn6)
//        val btn7: Button = findViewById(R.id.btn7)
//        val btn8: Button = findViewById(R.id.btn8)
//        val btn9: Button = findViewById(R.id.btn9)
//        val decimalPoint: Button = findViewById(R.id.decimalpoint)
//
//        val plus: Button = findViewById(R.id.plus)
//        val minus: Button = findViewById(R.id.minus)
//        val multiply: Button = findViewById(R.id.multyply)
//        val divide: Button = findViewById(R.id.devide)
//        val equalsBtn: Button = findViewById(R.id.equals)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        val toggleListener = View.OnClickListener {
            if(newNumber.text.toString() == "" || newNumber.text.toString().toDouble() == 0.0) {
                return@OnClickListener
            }

            newNumber.setText((-1 * (newNumber.text.toString().toDouble())).toString())
        }

        val clearBtnListener = View.OnClickListener {
            if(newNumber.text.toString() == "") {
                result.setText("")
            }

            newNumber.setText("")
            pendingOperation = "="
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
        decimalpoint.setOnClickListener(listener)
        toggleSign.setOnClickListener(toggleListener)
        clear.setOnClickListener(clearBtnListener)

        val oprListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                preformOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = pendingOperation
        }

        plus.setOnClickListener(oprListener)
        minus.setOnClickListener(oprListener)
        multyply.setOnClickListener(oprListener)
        devide.setOnClickListener(oprListener)
        equals.setOnClickListener(oprListener)
    }

    private fun preformOperation(value: Double, operation: String) {
        if (oprOne == null) {
            oprOne = value
        } else {
            if (pendingOperation == "=") {
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
        }

        result.setText(oprOne.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(oprOne != null) {
            outState.putDouble(STATE_OPRAND_ONE, oprOne!!)
            outState.putBoolean(STATE_OPR1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        oprOne = if(savedInstanceState.getBoolean(STATE_OPR1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPRAND_ONE)
        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
        operation.text = pendingOperation
    }
}
