package com.mekanly.presentation.ui.fragments.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mekanly.data.dataModels.DataBanner
import com.mekanly.data.retrofit.ApiClient.BASE_URL
import com.mekanly.databinding.ItemBannerBinding
import com.mekanly.databinding.ItemBigBannerBinding

class AdapterBigBanners(private val images: List<DataBanner>) :
    RecyclerView.Adapter<AdapterBigBanners.BannerViewHolder>() {

    inner class BannerViewHolder(
        private val binding: ItemBigBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataBanner: DataBanner) {
            if (dataBanner.logo.isNotEmpty()) {
                Glide.with(binding.ivBannerAvatar).load(BASE_URL + dataBanner.parsedImages()[0])
                    .into(binding.ivBannerAvatar)
            }
            binding.tvInfoBanner.text = dataBanner.title
            if (dataBanner.image.isNotEmpty()) {
                Glide.with(binding.rivBanner).load(BASE_URL + dataBanner.parsedImages()[0])
                    .into(binding.rivBanner)

                Log.e("PARSING_ADDRESS", "bind: ${BASE_URL}${dataBanner.parsedImages()[0]}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBigBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size
}