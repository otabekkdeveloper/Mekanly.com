package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.BottomSheetItem
import com.mekanly.data.responseBody.DataHouseCategory


class BottomSheetAdapter(
    private val items: List<DataHouseCategory>,
    private val onClick: (DataHouseCategory) -> Unit
) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.findViewById<ImageView>(R.id.itemIcon)
        val title = view.findViewById<TextView>(R.id.itemTitle)

        fun bind(item: DataHouseCategory) {
            icon.setImageResource(R.drawable.ic_houses_for_sale)
            title.text = item.name
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
