package com.mekanly.adapters

import ImageSliderPagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.responseLocation.House
import com.mekanly.databinding.ItemPropertyBinding

class PropertyAdapter(private val items: List<House>) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPropertyBinding.bind(itemView)
        fun bind(item: House) = with(binding) {
            priceTextView.text = item.price

            Log.e("RESPONSE", "bind: "+item.price)
            categoryName.text = item.category_name
            name.text = item.name

            // Настраиваем ViewPager2 для слайдера изображений
            val imageSliderAdapter = ImageSliderPagerAdapter(item.images)
            imageSliderViewPager.adapter = imageSliderAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
