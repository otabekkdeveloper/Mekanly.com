package com.mekanly

import SatyjylarData
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mekanly.presentation.ui.adapters.AdapterSatyjylar
import com.mekanly.databinding.FragmentSatyjylarBinding


class SatyjylarFragment : Fragment() {
    private lateinit var binding: FragmentSatyjylarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatyjylarBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)



        val categories = listOf(
            SatyjylarData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_one, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_two, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_three, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
            SatyjylarData(R.drawable.satyjylar_four, "Emlak", "Bu satyjylar siziň islegiňizi kanagatlandyryp biler"),
        )

        binding.recyclerView.adapter = AdapterSatyjylar(categories)


        binding.location.setOnClickListener{
            LocationDialog()
        }


        return binding.root
    }



    private fun LocationDialog() {
        // Инфлейтим кастомный макет диалога
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_location_dialog, null)

        // Создаем диалог
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Настраиваем кнопки
        val btnGoybolsun = dialogView.findViewById<Button>(R.id.btnGoybolsun)
        val btnKabulEt = dialogView.findViewById<Button>(R.id.btnKabulEt)

        btnGoybolsun.setOnClickListener {
            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnKabulEt.setOnClickListener {
            Toast.makeText(requireContext(), "Принято", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }




        dialog.show()
    }


}