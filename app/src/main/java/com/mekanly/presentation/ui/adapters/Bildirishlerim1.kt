package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.BildirishlerimData

class Bildirishlerim1(private val items: List<BildirishlerimData>) : RecyclerView.Adapter<Bildirishlerim1.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btn_like) // Настроить кнопку здесь
        val imageView: ImageView = view.findViewById(R.id.imageViewBildirishlerim)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adv_big, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.titleTextView.text = item.title
        holder.subtitleTextView.text = item.subtitle

        // Добавить логику для кнопки, если нужно
        holder.button.setOnClickListener {
            // Добавьте действия для кнопки здесь
        }
    }

    override fun getItemCount() = items.size
}
