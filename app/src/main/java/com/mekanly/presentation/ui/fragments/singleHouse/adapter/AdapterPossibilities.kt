package com.mekanly.presentation.ui.fragments.singleHouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.dataModels.DataPossibility
import com.mekanly.presentation.ui.enums.Possibilities

class AdapterPossibilities(
    private val possibilities: List<DataPossibility>
) : RecyclerView.Adapter<AdapterPossibilities.PossibilityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PossibilityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opportunity_in_single_house, parent, false)
        return PossibilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: PossibilityViewHolder, position: Int) {
        val possibility = possibilities[position]
        holder.bind(possibility)
    }

    override fun getItemCount(): Int = possibilities.size

    inner class PossibilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.imageView20)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewOpportunity)

        fun bind(possibility: DataPossibility) {
            val possibilityEnum = Possibilities.entries.find {
                it.key.equals(possibility.name, ignoreCase = true) ||
                        it.name.equals(possibility.name, ignoreCase = true)
            }
            val name:String = when (possibilityEnum) {
                Possibilities.WIFI -> ContextCompat.getString(nameTextView.context,R.string.wifi)
                Possibilities.WASHER -> ContextCompat.getString(nameTextView.context,R.string.washer)
                Possibilities.TV -> ContextCompat.getString(nameTextView.context,R.string.tv)
                Possibilities.CONDITIONER -> ContextCompat.getString(nameTextView.context,R.string.conditioner)
                Possibilities.WARDROBE ->ContextCompat.getString(nameTextView.context,R.string.wardrobe)
                Possibilities.BED ->ContextCompat.getString(nameTextView.context,R.string.bed)
                Possibilities.HOT ->ContextCompat.getString(nameTextView.context,R.string.hot)
                Possibilities.FRIDGE ->ContextCompat.getString(nameTextView.context,R.string.fridge)
                Possibilities.SHOWER ->ContextCompat.getString(nameTextView.context,R.string.shower)
                Possibilities.KITCHEN -> ContextCompat.getString(nameTextView.context,R.string.kitchen)
                Possibilities.HOT_WATER -> ContextCompat.getString(nameTextView.context,R.string.hot_water)
                else-> ""
            }
            nameTextView.text = name

            val iconResourceId = when (possibilityEnum) {
                Possibilities.WIFI -> R.drawable.ic_wifi
                Possibilities.WASHER -> R.drawable.ic_washing_machine
                Possibilities.TV -> R.drawable.ic_tv
                Possibilities.CONDITIONER -> R.drawable.ic_air_conditioner
                Possibilities.WARDROBE -> R.drawable.ic_wardrobe
                Possibilities.BED -> R.drawable.ic_bedroom
                Possibilities.HOT_WATER -> R.drawable.ic_hot_water
                Possibilities.FRIDGE -> R.drawable.ic_fridge
                Possibilities.SHOWER -> R.drawable.ic_bath
                Possibilities.KITCHEN -> R.drawable.ic_kitchen
                else -> R.drawable.ic_wifi
            }
            
            iconImageView.setImageResource(iconResourceId)
        }
    }
}