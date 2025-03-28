package com.mekanly.presentation.ui.fragments.businessProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekanly.databinding.FragmentDetailsSubBusinessBinding


class FragmentDetailsSubBusiness : Fragment() {
    private lateinit var binding: FragmentDetailsSubBusinessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsSubBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

}