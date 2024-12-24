package com.mekanly.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.presentation.ui.activities.main.MainActivity
import com.mekanly.R
import com.mekanly.databinding.FragmentConfirmationBinding

class FragmentConfirmation : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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



