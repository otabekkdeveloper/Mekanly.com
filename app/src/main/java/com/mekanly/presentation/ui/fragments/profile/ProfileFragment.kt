package com.mekanly.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)





        binding.btnLanguage.setOnClickListener{
            findNavController().navigate((R.id.action_homeFragment_to_languageFragment))
        }

        binding.addHouse.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentHome_to_addNotificationFragment)
        }




        return binding.root
    }
}
