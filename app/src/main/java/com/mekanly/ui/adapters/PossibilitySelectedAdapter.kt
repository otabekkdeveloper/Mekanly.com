package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemOpportunityDialogBinding
import com.mekanly.presentation.ui.enums.Possibilities

class PossibilitySelectedAdapter : RecyclerView.Adapter<PossibilitySelectedAdapter.OptionViewHolder>() {

    private val selectedList = mutableListOf<Option>()

    fun setOptions(options: List<Option>) {
        selectedList.clear()
        selectedList.addAll(options)
        notifyDataSetChanged()
    }

    inner class OptionViewHolder(val binding: ItemOpportunityDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.textView.text = option.name

            // Загрузка иконки из строки (если это URL, нужно использовать Glide или Coil)
            option.icon?.let {
                Glide.with(binding.imageView.context)
                    .load(it)
                    .into(binding.imageView)
            }


            val possibilityEnum = Possibilities.entries.find {
                it.key.equals(option.name, ignoreCase = true) ||
                        it.name.equals(option.name, ignoreCase = true)
            }

            val context = binding.textView.context
            binding.textView.text = when (possibilityEnum) {
                Possibilities.WIFI -> context.getString(R.string.wifi)
                Possibilities.WASHER -> context.getString(R.string.washer)
                Possibilities.TV -> context.getString(R.string.tv)
                Possibilities.CONDITIONER -> context.getString(R.string.conditioner)
                Possibilities.WARDROBE -> context.getString(R.string.wardrobe)
                Possibilities.BED -> context.getString(R.string.bed)
                Possibilities.HOT -> context.getString(R.string.hot)
                Possibilities.FRIDGE -> context.getString(R.string.fridge)
                Possibilities.SHOWER -> context.getString(R.string.shower)
                Possibilities.KITCHEN -> context.getString(R.string.kitchen)
                Possibilities.HOT_WATER -> context.getString(R.string.hot_water)
                else -> option.name
            }

            val iconRes = when (possibilityEnum) {
                Possibilities.WIFI -> R.drawable.ic_wifi
                Possibilities.POOL -> R.drawable.ic_swimming_pool
                Possibilities.BALCONY -> R.drawable.ic_balcony
                Possibilities.ELEVATOR -> R.drawable.ic_lift
                Possibilities.KITCHEN_FURNITURE -> R.drawable.ic_kitchen_furniture
                Possibilities.WASHER -> R.drawable.ic_washing_machine
                Possibilities.TV -> R.drawable.ic_tv
                Possibilities.HOT -> R.drawable.ic_heating_system
                Possibilities.STOVE -> R.drawable.ic_stove
                Possibilities.WORK_DESK -> R.drawable.ic_table
                Possibilities.CONDITIONER -> R.drawable.ic_air_conditioner
                Possibilities.WARDROBE -> R.drawable.ic_wardrobe
                Possibilities.BED -> R.drawable.ic_bedroom
                Possibilities.MANGAL -> R.drawable.ic_bbq
                Possibilities.HOT_WATER -> R.drawable.ic_hot_water
                Possibilities.FRIDGE -> R.drawable.ic_fridge
                Possibilities.SHOWER -> R.drawable.ic_bath
                Possibilities.KITCHEN -> R.drawable.ic_kitchen
                else -> R.drawable.ic_wifi
            }

            binding.imageView.setImageResource(iconRes)









        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemOpportunityDialogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(selectedList[position])
    }

    override fun getItemCount(): Int = selectedList.size
}
