package com.mekanly.presentation.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mekanly.data.models.Location
import com.mekanly.data.repository.LocationRepository
import com.mekanly.databinding.FragmentLocationBinding

class FragmentLocation : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private val dataList = listOf<Location>()
    private val repository = LocationRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun setOnClickListeners(){

        binding.apply {

            backBtn.setOnClickListener{
                findNavController().popBackStack()
            }
        }


    }

}
