package com.kubiutmalhutiotar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Debug
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_roll2.*

class RollActivity : AppCompatActivity() {
    private val TAG = "RollActivity"
    private val diceArray = arrayOf(R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6)
    private val gameManager = GameManager
    private var num1 = 0
    private var num2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roll2)
        diceOne.setImageResource(R.drawable.blank1)
        diceTwo.setImageResource(R.drawable.blank1)

        startRoll(5000.0)
    }

    fun startRoll(rollTime: Double) {
        var curRollTime = 500.0;

        val timer = object: CountDownTimer(rollTime.toLong(), curRollTime.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                num1 = (1..6).random()
                num2 = (1..6).random()
                diceOne.setImageResource(diceArray[num1 - 1])
                diceTwo.setImageResource(diceArray[num2 - 1])
                Log.d(TAG, "dice1 " + num1)
                Log.d(TAG, "dice2 " + num2)
                curRollTime *= 1.3
            }
            override fun onFinish() {
                gameManager.rollDiceResult(num1 + num2)
                val intent = Intent(this@RollActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        timer.start()
    }
}
