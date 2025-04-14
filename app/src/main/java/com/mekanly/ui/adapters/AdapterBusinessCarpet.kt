package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.DataSubBusinessItem
import com.mekanly.databinding.ItemSubBusinessCategoriesBinding

// Адаптер для недвижимости
class AdapterBusinessCarpet(
    private val items: List<DataSubBusinessItem>,
    private val onItemClick: (DataSubBusinessItem) -> Unit
) : RecyclerView.Adapter<AdapterBusinessCarpet.RealEstateViewHolder>() {

    inner class RealEstateViewHolder(
        private val binding: ItemSubBusinessCategoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataSubBusinessItem) {

            binding.apply {


                // Безопасное установление текста с проверкой на null
                textView10?.text = item.name ?: ""

                // Безопасная установка изображения
                item.imageResId?.let {
                    imageView21?.setImageResource(it)
                }
                root.setOnClickListener {
                    onItemClick(item)


                }


            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RealEstateViewHolder {
        val binding = ItemSubBusinessCategoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RealEstateViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RealEstateViewHolder, position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}