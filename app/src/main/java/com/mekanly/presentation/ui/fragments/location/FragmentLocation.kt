package com.mekanly.presentation.ui.fragments.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentLocationBinding


class FragmentLocation : Fragment() {
    private lateinit var binding: FragmentLocationBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationBinding.inflate(inflater, container, false)



        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }



        return binding.root
    }


    }
