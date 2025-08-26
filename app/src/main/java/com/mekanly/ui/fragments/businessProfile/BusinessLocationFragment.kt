package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentBusinessLocationBinding


class BusinessLocationFragment : Fragment() {

    private lateinit var binding: FragmentBusinessLocationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBusinessLocationBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }

    private fun initListeners() {

        binding.apply {

            backBtn.setOnClickListener{
                findNavController().popBackStack()
            }

        }


    }


}