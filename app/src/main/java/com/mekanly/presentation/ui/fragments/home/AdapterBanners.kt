package com.mekanly.presentation.ui.fragments.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.R
import com.mekanly.data.dataModels.DataBanner
import com.mekanly.data.retrofit.ApiClient.BASE_URL

class AdapterBanners(private val images: List<DataBanner>) :
    RecyclerView.Adapter<AdapterBanners.BannerViewHolder>() {

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.riv_banner)
        fun bind(dataBanner: DataBanner){
            if (dataBanner.image.isNotEmpty()){
                Glide.with(imageView).load(BASE_URL+dataBanner.parsedImages()[0]).into(imageView)
                Log.e("PARSING_ADDRESS", "bind: "+BASE_URL+dataBanner.parsedImages()[0] )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = images[position]
        holder.bind(banner)
    }

    override fun getItemCount(): Int = images.size
}
