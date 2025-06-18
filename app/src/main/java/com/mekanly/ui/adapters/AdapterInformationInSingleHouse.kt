package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R

data class HouseItem(
    val iconResId: Int, // ресурс изображения
    val category: String,
    val title: String
)

class AdapterInformationInSingleHouse(private val items: List<HouseItem>) : RecyclerView.Adapter<AdapterInformationInSingleHouse.HouseViewHolder>() {

    inner class HouseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.imageView10)
        private val category: TextView = view.findViewById(R.id.textView7)
        private val title: TextView = view.findViewById(R.id.textView6)

        fun bind(item: HouseItem) {
            icon.setImageResource(item.iconResId)
            category.text = item.category
            title.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_information_in_single_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
