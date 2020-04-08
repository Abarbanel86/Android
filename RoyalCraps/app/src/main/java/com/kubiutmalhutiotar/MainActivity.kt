package com.kubiutmalhutiotar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val gameManager: GameManager = GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalCash.text = gameManager.totalCash.toString()

        setListeners()
    }

    private fun setListeners() {

        roll.setOnClickListener {
            val intent = Intent(this, RollActivity::class.java)
            startActivity(intent)
        }

        onClickFunction(rollResult2, coinsForRollsOf2)
        onClickFunction(rollResualt3, coinsForRollsOf3)
        onClickFunction(rollResult4, coinsForRollsOf4)
        onClickFunction(rollResult5, coinsForRollsOf5)
        onClickFunction(rollResult6, coinsForRollsOf6)
        onClickFunction(rollResult7, coinsForRollsOf7)
        onClickFunction(rollResult8, coinsForRollsOf8)
        onClickFunction(rollResult9, coinsForRollsOf9)
        onClickFunction(rollResult10, coinsForRollsOf10)
        onClickFunction(rollResult11, coinsForRollsOf11)
        onClickFunction(rollResult12, coinsForRollsOf12)

        onClickFunction(rollResultDouble, coinsForRollsOfDouble)
        onClickFunction(rollResultOdds, coinsForRollResultOdds)
        onClickFunction(rollResultEvens, coinsForRollsOfEvens)
    }

    private fun onClickFunction(btn: Button, img: ImageView) {

        btn.setOnClickListener {

            when (img.tag.toString()) {
                "blank" -> {
                    img.setImageResource(R.drawable.chip10)
                    img.setTag("coins10")
                    gameManager.makeBet(btn.text.toString(), 10, this)
                }
                "coins10" -> {
                    img.setImageResource(R.drawable.chip20)
                    img.setTag("coins20")
                    gameManager.makeBet(btn.text.toString(), 10, this)
                }
                "coins20" -> {
                    img.setImageResource(R.drawable.chip50)
                    img.setTag("coins50")
                    gameManager.makeBet(btn.text.toString(), 30, this)
                }
                "coins50" -> {
                    img.setImageResource(R.drawable.chip100)
                    img.setTag("coins100")
                    gameManager.makeBet(btn.text.toString(), 50, this)
                }
                "coins100" -> {
                    img.setImageResource(R.drawable.blank1)
                    img.setTag("blank")
                    gameManager.makeBet(btn.text.toString(), -100, this)
                }

                else ->
                    Log.d(TAG, "I don't know this button")
            }

            totalCash.text = gameManager.totalCash.toString()
        }
    }
}
