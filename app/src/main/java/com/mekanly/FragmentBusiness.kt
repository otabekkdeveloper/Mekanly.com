package com.mekanly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.DataItemBusinessProfile
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterItemBusinessProfile


class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)





        val items = listOf(
            DataItemBusinessProfile(R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(R.drawable.placeholder, "Rowaç mebel", "Gyssagly satlyk jaý gerek!!!"),
            DataItemBusinessProfile(R.drawable.home_villa_image, "Täze öý", "Jaý amatly we täze."),
            DataItemBusinessProfile(R.drawable.model_house, "Satlyk dükan", "Dükan doly enjamlaşdyrylan.")
        )

        val adapter = AdapterItemBusinessProfile(items)
        binding.rvBusinessProfiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBusinessProfiles.adapter = adapter



        return binding.root
    }






}