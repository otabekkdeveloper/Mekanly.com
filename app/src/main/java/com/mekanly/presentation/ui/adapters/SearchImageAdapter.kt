package com.mekanly.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.DataPost

class SearchImageAdapter(private val items: List<DataPost>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SIMPLE_POST = 1
        const val LISTED_POST = 2
    }

    override fun getItemViewType(position: Int): Int {
        return  items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SIMPLE_POST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search, parent, false)
                VerticalViewHolder(view)
            }
            LISTED_POST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_horizontal, parent, false)
                HorizontalViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VerticalViewHolder -> {
                val item = items[position]
                holder.bind(item)
            }
            is HorizontalViewHolder -> {
                val item = items[position]
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class VerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: DataPost) {
            if(item.imageItem!=null){
                itemImage.setImageResource(item.imageItem)
            }
        }
    }

    inner class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val horizontalRecyclerView: RecyclerView = itemView.findViewById(R.id.horizontalRecyclerView)

        fun bind(post: DataPost) {
            if (post.inner==null) return@bind
            val innerResourceList:MutableList<Int> = mutableListOf()
            post.inner.forEach{
                innerResourceList.add(it.imageItem?:0)
            }
            horizontalRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            horizontalRecyclerView.adapter = ImageAdapter(innerResourceList)
        }
    }
}
