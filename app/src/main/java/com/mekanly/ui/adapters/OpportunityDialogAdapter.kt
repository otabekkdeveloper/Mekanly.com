package com.mekanly.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.OpportunityData

class OpportunityDialogAdapter(
    private val items: List<OpportunityData>,
    private val selectedItems: List<OpportunityData>,
    private val onClick: (OpportunityData) -> Unit
) : RecyclerView.Adapter<OpportunityDialogAdapter.ButtonViewHolder>() {


    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.imageView)
        private val text: TextView = view.findViewById(R.id.textView)
        private val layBackground: LinearLayout = view.findViewById(R.id.layBackground)


        fun bind(item: OpportunityData) {
            icon.setBackgroundResource(item.iconResId)
            text.text = item.text

            if (item in selectedItems) layBackground.setBackgroundResource(R.drawable.bg_selected_properties_btn)
            else {
                layBackground.setBackgroundResource(R.drawable.emlakler_btn_bg)
            }

            itemView.setOnClickListener {
                onClick(item)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opportunity_dialog, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
