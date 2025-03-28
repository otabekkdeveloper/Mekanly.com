package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.DataItemBusinessCategories
import com.mekanly.R
import com.mekanly.databinding.ItemCategoriesBusinessBinding


class AdapterItemBusinessCategories(
    private val items: List<DataItemBusinessCategories>,
    private val onItemClick: ((DataItemBusinessCategories) -> Unit)? = null
) : RecyclerView.Adapter<AdapterItemBusinessCategories.BusinessProfileViewHolder>() {

    inner class BusinessProfileViewHolder(
        private val binding: ItemCategoriesBusinessBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItemBusinessCategories) {
            binding.apply {
                // Безопасное установление текста с проверкой на null
                textView10?.text = item.title ?: ""

               count.text = item.count



                // Безопасная установка изображения
                item.imageResId?.let {
                    imageView21?.setImageResource(it)
                }

                // Обработка клика с проверкой на null
                root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessProfileViewHolder {
        val binding = ItemCategoriesBusinessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BusinessProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessProfileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}