package com.mekanly

import SatyjylarData
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.databinding.FragmentBusinessBinding
import com.mekanly.presentation.ui.adapters.AdapterSatyjylar


class FragmentBusiness : Fragment() {
    private lateinit var binding: FragmentBusinessBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)



        val categories = listOf(
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.placeholder, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
        )

//        binding.recyclerView.adapter = AdapterSatyjylar(categories)


//        binding.location.setOnClickListener{
//            LocationDialog()
//        }


        return binding.root
    }






}