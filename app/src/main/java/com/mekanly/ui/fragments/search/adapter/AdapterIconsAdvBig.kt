package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemIconsAdvBigBinding
import com.mekanly.ui.enums.Possibilities

class AdapterIconsAdvBig(
    private val possibilities: List<Option>
) : RecyclerView.Adapter<AdapterIconsAdvBig.PropertyIconViewHolder>() {

    inner class PropertyIconViewHolder(private val binding: ItemIconsAdvBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(option: Option) {
            val iconRes = when (option.id) {
                1 -> R.drawable.ic_wifi
                2 -> R.drawable.ic_washing_machine
                3 -> R.drawable.ic_tv
                4 -> R.drawable.ic_air_conditioner
                5 -> R.drawable.ic_wardrobe
                6 -> R.drawable.ic_bedroom
                7, 19 -> R.drawable.ic_hot_water // Оба ID для горячей воды
                8 -> R.drawable.ic_fridge
                9 -> R.drawable.ic_bath
                10 -> R.drawable.ic_kitchen
                11 -> R.drawable.ic_bbq
                12 -> R.drawable.ic_swimming_pool
                13 -> R.drawable.ic_kitchen_furniture
                14 -> R.drawable.ic_balcony
                15 -> R.drawable.ic_work_desk
                16 -> R.drawable.ic_lift
                17 -> R.drawable.ic_stove
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