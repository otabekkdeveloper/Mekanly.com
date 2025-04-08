package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentBusinessCategoriesBinding
import com.mekanly.databinding.FragmentBusinessFilterBinding


class BusinessFilterFragment : Fragment() {

   private lateinit var binding: FragmentBusinessFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBusinessFilterBinding.inflate(inflater, container, false)

        initListeners()


        return binding.root
    }

    private fun initListeners() {

        binding.apply {


            buttonBolum.setOnClickListener{

                findNavController().navigate(R.id.action_businessFilterFragment_to_businessCategoriesFragment)

            }


        }


    }

}