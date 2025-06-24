package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemIconsAdvBigBinding
import com.mekanly.presentation.ui.enums.Possibilities

class AdapterIconsAdvBig    (
    private val possibilities: List<Option>
) : RecyclerView.Adapter<AdapterIconsAdvBig.PropertyIconViewHolder>() {

    inner class PropertyIconViewHolder(private val binding: ItemIconsAdvBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(option: Option) {
            val iconRes = when (option.name) {
                Possibilities.WIFI.key -> R.drawable.ic_wifi
                Possibilities.WASHER.key -> R.drawable.ic_washing_machine
                Possibilities.TV.key -> R.drawable.ic_tv
                Possibilities.CONDITIONER.key -> R.drawable.ic_air_conditioner
                Possibilities.WARDROBE.key -> R.drawable.ic_wardrobe
                Possibilities.BED.key -> R.drawable.ic_bedroom
                Possibilities.HOT.key -> R.drawable.ic_heating_system
                Possibilities.FRIDGE.key -> R.drawable.ic_fridge
                Possibilities.SHOWER.key -> R.drawable.ic_bath
                Possibilities.KITCHEN.key -> R.drawable.ic_kitchen
                Possibilities.MANGAL.key -> R.drawable.ic_bbq
                Possibilities.POOL.key -> R.drawable.ic_swimming_pool
                Possibilities.KITCHEN_FURNITURE.key -> R.drawable.ic_kitchen_furniture
                Possibilities.BALCONY.key -> R.drawable.ic_balcony
                Possibilities.WORK_DESK.key -> R.drawable.ic_work_desk
                Possibilities.ELEVATOR.key -> R.drawable.ic_lift
                Possibilities.STOVE.key -> R.drawable.ic_stove
                Possibilities.HOT_WATER.key -> R.drawable.ic_hot_water
                else -> null
            }

            iconRes?.let {
                binding.ivPropertyIcon.setImageResource(it)
                binding.root.visibility = View.VISIBLE
            } ?: run {
                binding.root.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyIconViewHolder {
        val binding = ItemIconsAdvBigBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PropertyIconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyIconViewHolder, position: Int) {
        holder.bind(possibilities[position])
    }

    override fun getItemCount() = possibilities.size
}