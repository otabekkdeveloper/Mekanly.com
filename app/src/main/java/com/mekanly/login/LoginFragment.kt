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
import com.mekanly.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        initListeners()
        return binding.root
    }

    private fun initListeners() {

        binding.ulgamaGir.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.btnSignUp.setOnClickListener {
//            binding.inputPhone.error = "Please write your phone number"
//            binding.inputPassword.error = "Please write your phone number"
            if (binding.yourName.text.isEmpty() || binding.yourName.text.length < 4) {
                binding.yourName.error = "Please write your phone number"
                return@setOnClickListener
            }
            if (binding.phoneNumber.text.isEmpty() || binding.phoneNumber.text.length < 4) {
                binding.phoneNumber.error = "Please write your phone number"
                return@setOnClickListener
            }

            if (binding.yourPassword.text.isEmpty() || binding.yourPassword.text.length < 4) {
                binding.yourPassword.error = "Please write your password"
                return@setOnClickListener
            }
            if (binding.replayPassword.text.isEmpty() || binding.replayPassword.text.length < 4) {
                binding.replayPassword.error = "Please write your password"
                return@setOnClickListener
            }


            binding.btnSignUp.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

            }


        }


    }
}


