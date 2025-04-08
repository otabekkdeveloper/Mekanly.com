package com.mekanly.presentation.ui.bottomSheet

import LocationBottomSheet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.databinding.FragmentBusinessFilterBottomSheetBinding

class BusinessFilterFragmentBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentBusinessFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var onDeleteCallback: (() -> Unit)? = null

    companion object {
        fun newInstance(onDelete: () -> Unit): BusinessFilterFragmentBottomSheet {
            return BusinessFilterFragmentBottomSheet().apply {
                this.onDeleteCallback = onDelete
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}