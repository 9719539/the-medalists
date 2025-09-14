package com.example.themedalists

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.themedalists.models.Medalist

class Adapter(private val list: List<Medalist>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the medalist_item view
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.medalist_item, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medalist = list[position]

        // sets the text to the textview from our itemHolder class
        holder.countryView.text = medalist.country
        holder.iocView.text = medalist.iocCode
        holder.timesCompetedView.text = medalist.timesCompeted.toString()

        // set click listener for the item view
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, medalist)
        }

        // set the colour of the drawable border and width to make it more visible
        val cardView = holder.itemView as com.google.android.material.card.MaterialCardView
        val medalIcon = cardView.findViewById<ImageView>(R.id.medalIcon)
        val medalCount = cardView.findViewById<TextView>(R.id.medalCount)

        // reset colour and visibility to handle view recycling
        medalIcon.visibility = View.VISIBLE
        medalIcon.clearColorFilter()

        when {
            medalist.bronzeMedals > 0 && medalist.silverMedals < 1 && medalist.goldMedals < 1 -> {
                medalIcon.setColorFilter(ContextCompat.getColor(holder.context, R.color.medalBronze))
                medalCount.text = medalist.bronzeMedals.toString()
            }
            medalist.silverMedals > 0 && medalist.goldMedals < 1 -> {
                medalIcon.setColorFilter(ContextCompat.getColor(holder.context, R.color.medalSilver))
                medalCount.text = medalist.silverMedals.toString()
            }
            medalist.goldMedals > 0 ->  {
                medalIcon.setColorFilter(ContextCompat.getColor(holder.context, R.color.medalGold))
                medalCount.text = medalist.goldMedals.toString()
            }
            else -> {
                medalIcon.visibility = View.GONE // Hide icon if no medals
                medalCount.text = "0"
            }
        }
    }

    // set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: Medalist)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // holds the views for adding it to image and text
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var context: Context = itemView.context
        val countryView: TextView = itemView.findViewById(R.id.country)
        val iocView: TextView = itemView.findViewById(R.id.iocCode)
        val timesCompetedView: TextView = itemView.findViewById(R.id.timesCompeted)
    }


}