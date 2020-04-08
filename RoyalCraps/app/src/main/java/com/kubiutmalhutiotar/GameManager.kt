package com.kubiutmalhutiotar

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.lang.NumberFormatException
import java.util.*
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

object GameManager {
    val TAG = "gameManager"
    var totalCash = 1000
    private var numberBets = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var betOnEvens = 0
    private var betOnOdds = 0
    private var betOnDouble = 0

    fun makeBet(btnTag: String, betAmount:Int, context: Context){
        if(betAmount > totalCash) {
            Toast.makeText(context, "Not Enough Cash", Toast.LENGTH_LONG).show()
        } else {
            totalCash -= betAmount

            when(btnTag) {
                "evens" -> betOnEvens += betAmount
                "odds" -> betOnOdds += betAmount
                "double" -> betOnDouble += betAmount

                else -> {
                    try {
                        val bet = btnTag.toInt()
                        numberBets[bet - 2] += betAmount
                    } catch(e: NumberFormatException) {
                        Log.e(TAG, "not a number!")
                    }
                }
            }
        }
    }

    fun rollDiceResult(res: Int) {
//        val res =
        val res = 7.0
        Log.d(TAG, "roll result " + res)

        Log.d(TAG, "multiplying by... " + (abs(7 - res)) / 10)
        totalCash += (numberBets[(res - 2.0).toInt()] * (2 + abs(7 - res) / 5)).toInt()
        Log.d(TAG, "total cash " + totalCash)

        numberBets = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    }
}