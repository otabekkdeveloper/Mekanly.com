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
    private val items: List<PropertiesDialogData>,
    private val onItemClick: (PropertiesDialogData) -> Unit
) : RecyclerView.Adapter<PropertiesDialogAdapter.PropertiesViewHolder>() {

    private val selectedItems = MutableList(items.size) { false }

    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val buttonLayout: LinearLayout? = itemView.findViewById(R.id.btnKwartira)
        private val textView: TextView? = itemView.findViewById(R.id.textView)
        private val imageView: ImageView? = itemView.findViewById(R.id.imageView)

        fun bind(item: PropertiesDialogData, position: Int) {
            textView?.text = item.name
            imageView?.setBackgroundResource(item.imageResId)

            buttonLayout?.setBackgroundResource(
                if (selectedItems[position]) R.drawable.bg_selected_properties_btn
                else R.drawable.emlakler_btn_bg
            )

            buttonLayout?.setOnClickListener {
                selectedItems[position] = !selectedItems[position]
                notifyItemChanged(position)
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_properties, parent, false)
        return PropertiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    // Метод для выделения или сброса всех кнопок
    fun setAllSelected(selected: Boolean) {
        selectedItems.fill(selected)
        notifyDataSetChanged()
    }
}
