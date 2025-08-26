package com.mekanly.presentation.ui.fragments.singleHouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.ui.enums.Possibilities

class AdapterPossibilities(
    private val possibilities: List<Option>
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

        fun bind(option: Option) {
            val possibilityEnum = Possibilities.fromId(option.id)

            val name: String = when (possibilityEnum?.id) {
                1 -> ContextCompat.getString(nameTextView.context, R.string.wifi)
                2 -> ContextCompat.getString(nameTextView.context, R.string.washer)
                3 -> ContextCompat.getString(nameTextView.context, R.string.tv)
                4 -> ContextCompat.getString(nameTextView.context, R.string.conditioner)
                5 -> ContextCompat.getString(nameTextView.context, R.string.wardrobe)
                6 -> ContextCompat.getString(nameTextView.context, R.string.bed)
                7, 19 -> ContextCompat.getString(nameTextView.context, R.string.hot_water)
                8 -> ContextCompat.getString(nameTextView.context, R.string.fridge)
                9 -> ContextCompat.getString(nameTextView.context, R.string.shower)
                10 -> ContextCompat.getString(nameTextView.context, R.string.kitchen)
                11 -> ContextCompat.getString(nameTextView.context, R.string.mangal)
                12 -> ContextCompat.getString(nameTextView.context, R.string.basseýn)
                13 -> ContextCompat.getString(nameTextView.context, R.string.kitchen_mebel)
                14 -> ContextCompat.getString(nameTextView.context, R.string.balcony)
                15 -> ContextCompat.getString(nameTextView.context, R.string.work_table)
                16 -> ContextCompat.getString(nameTextView.context, R.string.lift)
                17 -> ContextCompat.getString(nameTextView.context, R.string.pech)
                else -> option.name // Если не найдено, используем оригинальное имя
            }
            nameTextView.text = name

            val iconResourceId = when (possibilityEnum?.id) {
                1 -> R.drawable.ic_wifi
                2 -> R.drawable.ic_washing_machine
                3 -> R.drawable.ic_tv
                4 -> R.drawable.ic_air_conditioner
                5 -> R.drawable.ic_wardrobe
                6 -> R.drawable.ic_bedroom
                7, 19 -> R.drawable.ic_hot_water
                8 -> R.drawable.ic_fridge
                9 -> R.drawable.ic_bath
                10 -> R.drawable.ic_kitchen
                11 -> R.drawable.ic_bbq
                12 -> R.drawable.ic_swimming_pool
                13 -> R.drawable.ic_kitchen_furniture
                14 -> R.drawable.ic_balcony
                15 -> R.drawable.ic_table
                16 -> R.drawable.ic_lift
                17 -> R.drawable.ic_stove
                else -> R.drawable.ic_wifi // Иконка по умолчанию
            }

            iconImageView.setImageResource(iconResourceId)
        }
    }
}