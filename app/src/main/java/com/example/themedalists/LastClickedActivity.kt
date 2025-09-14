package com.example.themedalists

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.themedalists.models.Medalist
import com.google.gson.Gson

class LastClickedActivity : AppCompatActivity() {
    // key for storing last clicked item.
    val LAST_CLICKED_KEY: String = "lastClickedItem"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // variable for shared preferences.
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        setContentView(R.layout.last_item_clicked)

        val lastClickedMedalist = sharedPreferences.getString(LAST_CLICKED_KEY, "")
        val medalist = Gson().fromJson(lastClickedMedalist, Medalist::class.java)

        // get the text view
        val lastClickedText: TextView = findViewById(R.id.lastClicked)

        // if no medalist has been saved
        if (lastClickedMedalist.isNullOrEmpty()) {
            lastClickedText.text = "No country has been clicked."
        } else {
            lastClickedText.text = "The last country clicked was ${medalist.country} (${medalist.iocCode})."

        }
    }
}