package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option

class AdapterOpportunityInSingleHouse(private val items: List<Option>) : RecyclerView.Adapter<AdapterOpportunityInSingleHouse.HouseViewHolder>() {

    inner class HouseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.imageView20)
        private val category: TextView = view.findViewById(R.id.textViewOpportunity)

        fun bind(item: Option) {
//            icon.setImageResource(item.iconResId)
//            category.text = item.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_opportunity_in_single_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
