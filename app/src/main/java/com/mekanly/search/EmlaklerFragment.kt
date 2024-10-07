package com.mekanly.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.adapters.ImageAdapter
import com.mekanly.adapters.PostAdapter
import com.mekanly.adapters.SearchImageAdapter
import com.mekanly.databinding.FragmentEmlaklerBinding


class EmlaklerFragment : Fragment() {
    private lateinit var binding: FragmentEmlaklerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmlaklerBinding.inflate(inflater, container, false)


        val imagesTwo = listOf(
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,
            R.drawable.search_house_image,)

            // Устанавливаем адаптер
            binding.recyclerViewTwo.adapter = SearchImageAdapter(imagesTwo)

                    // Устанавливаем горизонтальный LayoutManager
                    binding . recyclerViewTwo . layoutManager = LinearLayoutManager (requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )




        return binding.root
    }


}

