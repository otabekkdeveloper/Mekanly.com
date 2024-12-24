package com.mekanly.presentation.ui.fragments.businessProfiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.R
import com.mekanly.presentation.ui.adapters.AdapterAdvertisements
import com.mekanly.data.BildirishlerimData
import com.mekanly.databinding.FragmentBildirshlerimOneBinding

class BusinessProfilesSubFragment : Fragment() {
    private lateinit var binding: FragmentBildirshlerimOneBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBildirshlerimOneBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val categories = listOf(
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
            BildirishlerimData(
                R.drawable.placeholder,
                "Emlak",
                "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"
            ),
        )

        binding.recyclerView.adapter = AdapterAdvertisements(categories)
        return binding.root
    }



}