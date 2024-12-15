package com.mekanly.presentation.ui.fragments.flow


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekanly.presentation.ui.adapters.PropertyAdapter
import com.mekanly.databinding.FragmentFlowBinding


class FlowFragment : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var propertyAdapter: PropertyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)


        // Настройка RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())



        return binding.root
    }


    }
