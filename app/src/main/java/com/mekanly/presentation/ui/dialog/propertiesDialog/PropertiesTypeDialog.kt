package com.mekanly.presentation.ui.dialog.propertiesDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mekanly.databinding.FragmentDialogPropertiesBinding

class PropertiesTypeDialog : Fragment() {
    private lateinit var binding: FragmentDialogPropertiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogPropertiesBinding.inflate(inflater, container, false)



        return binding.root
    }

}
