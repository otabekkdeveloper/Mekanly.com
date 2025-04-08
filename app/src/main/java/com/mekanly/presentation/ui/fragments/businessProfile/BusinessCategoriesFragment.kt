package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentBusinessCategoriesBinding


class BusinessCategoriesFragment : Fragment() {

    private lateinit var binding: FragmentBusinessCategoriesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBusinessCategoriesBinding.inflate(inflater, container, false)


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