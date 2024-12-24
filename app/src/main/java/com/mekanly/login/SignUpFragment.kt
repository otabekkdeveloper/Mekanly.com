package com.mekanly.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.presentation.ui.activities.main.MainActivity
import com.mekanly.R
import com.mekanly.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        initListeners()


        return binding.root


    }

    private fun initListeners() {
        binding.fragmentMail.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_fragmentMail)
        }



        binding.btnConfimation.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_fragmentConfirmation)

        }


    }
}



