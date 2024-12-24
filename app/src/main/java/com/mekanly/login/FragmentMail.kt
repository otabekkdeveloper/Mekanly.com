package com.mekanly.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentMailBinding


class FragmentMail : Fragment() {
    private lateinit var binding: FragmentMailBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMailBinding.inflate(inflater, container, false)


        binding.signUp.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentMail_to_signUpFragment)
        }

        binding.btnConfirmation.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentMail_to_fragmentOtp)
        }





        return binding.root
    }


    }
