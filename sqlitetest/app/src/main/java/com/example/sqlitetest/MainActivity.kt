package com.example.sqlitetest

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "mainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val db = baseContext.openOrCreateDatabase("qul-test-1.db", Context.MODE_PRIVATE, null)
//        db.execSQL("DROP TABLE IF EXISTS contacts3")
//        var sql = "CREATE TABLE IF NOT EXISTS contacts3(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
//        Log.d(TAG, "sql table created $sql")
//
//        db.execSQL(sql)
//        sql = "INSERT INTO contacts3(name, phone, email) VALUES('Tom', 213213213, 'tom@gmail.com')"
//        db.execSQL(sql)
//        Log.d(TAG, "sql inserted $sql")
//
//        val values = ContentValues().apply {
//            put("name", "Fred")
//            put("phone", 2312131)
//            put("email", "Fred@junk.com")
//        }
//
//        val generatedID = db.insert("contacts3", null, values)
//        Log.d(TAG, "sql data inserted data is $generatedID")

        val quarry = db.rawQuery("SELECT * FROM contacts3", null)
        quarry.use {
            while(it.moveToNext()) {
                with(it) {
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)
                    Log.d(TAG, "onCreatedb id = $id, name = $name,  phone = $phone, email = $email")
                }
            }
        }

        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
