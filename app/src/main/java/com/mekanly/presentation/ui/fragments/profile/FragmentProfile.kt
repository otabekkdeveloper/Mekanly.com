package com.mekanly.presentation.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.databinding.ActivityLoginBinding
import com.mekanly.databinding.FragmentProfileBinding
import com.mekanly.login.LoginActivity
import com.mekanly.presentation.ui.activities.main.MainActivity

class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val appPrefs by lazy {
        AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initListeners()
        return binding.root
    }

    private fun initView() {
        if (appPrefs.token==""){
            binding.apply {
                tvAccountName.text = "Press to log in"
                tvAccountNumber.text = ""
            }
        }else{
            binding.apply {
                tvAccountName.text = "Akkaunt"
                tvAccountNumber.text = appPrefs.username
            }
        }
    }

    private fun initListeners() {

        binding.hintFragment.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentHome_to_hintFragment)
        }

        binding.btnLanguage.setOnClickListener{
            findNavController().navigate((R.id.action_homeFragment_to_languageFragment))
        }

        binding.addHouse.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentHome_to_addNotificationFragment)
        }

        binding.crAccount.setOnClickListener {
            if (appPrefs.token == "") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "You have already logged in", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
