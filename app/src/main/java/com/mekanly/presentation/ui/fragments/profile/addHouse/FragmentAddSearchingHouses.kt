package com.mekanly.presentation.ui.fragments.profile.addHouse

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.databinding.FragmentAddSearchingHousesBinding


class FragmentAddSearchingHouses : Fragment() {
    private lateinit var binding: FragmentAddSearchingHousesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSearchingHousesBinding.inflate(inflater, container, false)

        rangeSeekBar()
        initListeners()




        return binding.root
    }


    private fun rangeSeekBar() {

        binding.rangeSeekBar.setValues(5f, 330f)

        binding.rangeSeekBar.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            val minValue = values[0]  // Левый ползунок
            val maxValue = values[1]  // Правый ползунок
            Log.d("RangeSlider", "Min: $minValue, Max: $maxValue")
        }


    }


    private fun initListeners() {

        binding.back.setOnClickListener{

            findNavController().popBackStack()

        }


    }


}