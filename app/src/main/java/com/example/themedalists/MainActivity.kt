package com.example.themedalists

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themedalists.models.Medalist
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    // key for storing last clicked item.
    val LAST_CLICKED_KEY: String = "lastClickedItem"
    // variable for shared preferences.
    lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        setContentView(R.layout.activity_main)

        // get the button for LastClickedItem
        val lastClickedBtn: Button = findViewById(R.id.lastSavedBtn)

        // create listener for btn
        lastClickedBtn.setOnClickListener {
             val lastClickedIntent = Intent(this@MainActivity, LastClickedActivity::class.java)
                startActivity(lastClickedIntent)
        }

        // get the recycler view
        val recyclerview: RecyclerView = findViewById(R.id.recyclerview)

        // create a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // create array list of our Medalist model
        val data = ArrayList<Medalist>()

        // read CSV from assets and populate data
        val inputStream = assets.open("medallists.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        var isFirstLine = true

        while (reader.readLine().also { line = it } != null) {
            if (isFirstLine) {
                isFirstLine = false // Skip header
                continue
            }
            val tokens = line!!.split(",")
            if (tokens.size >= 6) {
                val medalist = Medalist(
                    country = tokens[0],
                    iocCode = tokens[1],
                    timesCompeted = tokens[2].toIntOrNull() ?: 0,
                    goldMedals = tokens[3].toIntOrNull() ?: 0,
                    silverMedals = tokens[4].toIntOrNull() ?: 0,
                    bronzeMedals = tokens[5].toIntOrNull() ?: 0
                )
                data.add(medalist)
            }
        }
        reader.close()

        //  pass the array to the Adapter
        val adapter = Adapter(data)

        // set the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // create OnClickListener for the Adapter
        adapter.setOnClickListener(object:
         Adapter.OnClickListener {
            override fun onClick(position: Int, medalist: Medalist) {

                // create modal popup
                val detailsModal = DetailsModal.newInstance(medalist)
                detailsModal.show(supportFragmentManager,"DetailsModal")

                // save last clicked item
                saveItem(medalist)
            }
        })
    }

    // Save Medalist as JSON string
    private fun saveItem(medalist: Medalist) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val medalistJson = gson.toJson(medalist)
        editor.putString(LAST_CLICKED_KEY, medalistJson)
        editor.apply()
    }
}