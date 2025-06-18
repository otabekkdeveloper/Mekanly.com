package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.DataSubBusinessItem
import com.mekanly.databinding.ItemSubBusinessCategoriesBinding

// Адаптер для бизнес-активности
class AdapterBusinessFurniture(
    private val items: List<DataSubBusinessItem>,
    private val onItemClick: (DataSubBusinessItem) -> Unit
) : RecyclerView.Adapter<AdapterBusinessFurniture.BusinessActivityViewHolder>() {

    inner class BusinessActivityViewHolder(
        private val binding: ItemSubBusinessCategoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataSubBusinessItem) {
            binding.apply {


                // Безопасная установка изображения
                item.imageResId?.let {
                    imageView21?.setImageResource(it)
                }

                // Безопасное установление текста с проверкой на null
                textView10?.text = item.name ?: ""

                root.setOnClickListener {
                    onItemClick(item)


                }


            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BusinessActivityViewHolder {
        val binding = ItemSubBusinessCategoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BusinessActivityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BusinessActivityViewHolder, position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}