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
        binding.agzaBol.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.btnLogin.setOnClickListener {
            if (binding.inputPhone.text.isEmpty() || binding.inputPhone.text.length < 4) {
                binding.inputPhone.error = "Please write your phone number"
                return@setOnClickListener
            }

            if (binding.inputPassword.text.isEmpty() || binding.inputPassword.text.length < 4) {
                binding.inputPassword.error = "Please write your password"
                return@setOnClickListener
            }

            binding.btnLogin.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

            }
        }
    }


}
