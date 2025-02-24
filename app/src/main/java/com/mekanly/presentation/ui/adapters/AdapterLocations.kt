package com.mekanly.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.data.dataModels.DataLocation
import com.mekanly.databinding.ItemLocationsTextBinding

// Aдаптер для работы с данными
class AdapterLocations(private val context: Context, private val data: List<DataLocation>) :
    RecyclerView.Adapter<AdapterLocations.ItemViewHolder>() {

    // ViewHolder класс с использованием ViewBinding
    inner class ItemViewHolder(val binding: ItemLocationsTextBinding) : RecyclerView.ViewHolder(binding.root)

    // Создание ViewHolder с ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Используем ViewBinding для инфлейта макета
        val binding = ItemLocationsTextBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemViewHolder(binding)
    }

    // Привязка данных к ViewHolder с использованием ViewBinding
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val parentItem = data[position]
        holder.binding.textView.text = parentItem.name // Привязываем данные к элементу
    }

    // Возвращаем количество элементов
    override fun getItemCount(): Int {
        return data.size
    }
}
