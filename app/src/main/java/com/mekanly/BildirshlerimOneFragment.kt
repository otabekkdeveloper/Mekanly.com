package com.mekanly

import SatyjylarData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.adapters.Bildirishlerim1
import com.mekanly.data.BildirishlerimData
import com.mekanly.databinding.FragmentBildirshlerimOneBinding


class BildirshlerimOneFragment : Fragment() {
    private lateinit var binding: FragmentBildirshlerimOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBildirshlerimOneBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)



        val categories = listOf(
            BildirishlerimData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            BildirishlerimData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
        )

        binding.recyclerView.adapter = Bildirishlerim1(categories)





        return binding.root
    }



}