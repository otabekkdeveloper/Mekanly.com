package com.mekanly.presentation.ui.dialog.propertiesDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.PropertiesDialogData

class PropertiesDialogAdapter(
    private val items: List<PropertiesDialogData>, // Список данных
    private val onItemClick: (PropertiesDialogData) -> Unit // Лямбда для кликов
) : RecyclerView.Adapter<PropertiesDialogAdapter.PropertiesViewHolder>() {

    // Список для отслеживания состояния выбранности элементов
    private val selectedItems = MutableList(items.size) { false }

    // ViewHolder
    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val buttonLayout: LinearLayout? = itemView.findViewById(R.id.btnKwartira)
        private val textView: TextView? = itemView.findViewById(R.id.textView) // Название категории
        private val imageView: ImageView? = itemView.findViewById(R.id.imageView) // Иконка категории

        fun bind(item: PropertiesDialogData, position: Int) {
            // Устанавливаем текст и изображение
            textView?.text = item.name
            imageView?.setBackgroundResource(item.imageResId)

            // Устанавливаем фон в зависимости от состояния выбранности
            if (selectedItems[position]) {
                buttonLayout?.setBackgroundResource(R.drawable.bg_selected_properties_btn) // Выбранный фон
            } else {
                buttonLayout?.setBackgroundResource(R.drawable.emlakler_btn_bg) // Обычный фон
            }

            // Обработчик клика
            buttonLayout?.setOnClickListener {
                // Изменяем состояние выбранности
                selectedItems[position] = !selectedItems[position]

                // Обновляем элемент
                notifyItemChanged(position)

                // Вызываем лямбду клика
                onItemClick(item)
            }
        }
    }

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_properties, parent, false) // Макет для элемента
        return PropertiesViewHolder(view)
    }

    // Привязка данных
    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
