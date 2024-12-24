package com.mekanly.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekanly.R
import com.mekanly.databinding.FragmentMailBinding
import com.mekanly.databinding.FragmentOtpBinding
import com.mekanly.presentation.ui.activities.main.MainActivity


class FragmentOtp : Fragment() {
    private lateinit var binding: FragmentOtpBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentOtpBinding.inflate(inflater, container, false)


        binding.btnConfirmation.setOnClickListener{
            val intent = Intent( requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}