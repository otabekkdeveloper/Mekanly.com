package com.mekanly.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mekanly.databinding.FragmentConfirmationBinding

class FragmentConfirmation : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        initListeners()
        return binding.root
    }

    private fun initListeners() {



        }


    }



