package com.mekanly.presentation.ui.fragments.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mekanly.R

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_language, container, false)

        val radioGroup = view.findViewById<RadioGroup>(R.id.languageRadioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage = when (checkedId) {
                R.id.radioEnglish -> "English"
                R.id.radioRussian -> "Русский"
                R.id.radioTurkmen -> "Turkmen"
                else -> ""
            }

            // Show the selected language as a toast
            Toast.makeText(requireContext(), "Selected language: $selectedLanguage", Toast.LENGTH_SHORT).show()

            // You can save the selected language to preferences or handle it as needed
        }

        return view
    }
}
