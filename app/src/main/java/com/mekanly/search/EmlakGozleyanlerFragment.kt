package com.mekanly.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.adapters.EmlakGozleyanImageAdapter
import com.mekanly.databinding.FragmentEmlakGozleyanlerBinding


class EmlakGozleyanlerFragment : Fragment() {
    private lateinit var binding: FragmentEmlakGozleyanlerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmlakGozleyanlerBinding.inflate(inflater, container, false)

        val image = listOf(
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
            R.drawable.search_house_image)


        binding.recyclerViewThree.adapter = EmlakGozleyanImageAdapter(image)

        binding.recyclerViewThree.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        return binding.root
    }


            }

