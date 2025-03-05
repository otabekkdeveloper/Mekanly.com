package com.mekanly.presentation.ui.fragments.profile.addHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentAddHouseBinding
import com.mekanly.databinding.FragmentSingleHouseBinding


class FragmentAddHouse : Fragment() {

    private lateinit var binding: FragmentAddHouseBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddHouseBinding.inflate(inflater, container, false)


        binding.back.setOnClickListener{
            findNavController().popBackStack()
        }


        return binding.root
    }

}