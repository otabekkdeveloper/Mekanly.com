package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.models.Option
import com.mekanly.databinding.ItemOpportunityDialogBinding
import com.mekanly.ui.enums.Possibilities

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
            // Получаем enum по ID
            val possibilityEnum = Possibilities.fromId(option.id)

            // Устанавливаем текст в зависимости от ID
            val context = binding.textView.context
            binding.textView.text = when (option.id) {
                1 -> context.getString(R.string.wifi)
                2 -> context.getString(R.string.washer)
                3 -> context.getString(R.string.tv)
                4 -> context.getString(R.string.conditioner)
                5 -> context.getString(R.string.wardrobe)
                6 -> context.getString(R.string.bed)
                7, 19 -> context.getString(R.string.hot_water) // Оба ID для горячей воды
                8 -> context.getString(R.string.fridge)
                9 -> context.getString(R.string.shower)
                10 -> context.getString(R.string.kitchen)
                11 -> context.getString(R.string.mangal)
                12 -> context.getString(R.string.basseýn)
                13 -> context.getString(R.string.kitchen_mebel)
                14 -> context.getString(R.string.balcony)
                15 -> context.getString(R.string.work_table)
                16 -> context.getString(R.string.lift)
                17 -> context.getString(R.string.pech)
                else -> option.name // Если ID не найден, используем оригинальное имя
            }

            // Устанавливаем иконку
            val iconRes = when (option.id) {
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

            // Если есть кастомная иконка (URL), загружаем её, иначе используем ресурс
            option.icon?.let { iconUrl ->
                Glide.with(binding.imageView.context)
                    .load(iconUrl)
                    .placeholder(iconRes) // Показываем стандартную иконку пока грузится
                    .into(binding.imageView)
            } ?: run {
                binding.imageView.setImageResource(iconRes)
            }
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