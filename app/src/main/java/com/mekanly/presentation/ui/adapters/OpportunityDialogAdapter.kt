package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.OpportunityData

class OpportunityDialogAdapter(
    private val items: List<OpportunityData>,           // Список элементов
    private val onClick: (OpportunityData) -> Unit      // Обработчик клика
) : RecyclerView.Adapter<OpportunityDialogAdapter.ButtonViewHolder>() {

    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.imageView)
        private val text: TextView = view.findViewById(R.id.textView)

        fun bind(item: OpportunityData) {
            icon.setBackgroundResource(item.iconResId) // Установка иконки
            text.text = item.text                      // Установка текста

            itemView.setOnClickListener {
                onClick(item) // Вызов обработчика клика
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opportunity_dialog, parent, false) // Подключение вашего макета
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(items[position]) // Привязка данных к элементу
    }

    override fun getItemCount(): Int = items.size // Количество элементов
}
