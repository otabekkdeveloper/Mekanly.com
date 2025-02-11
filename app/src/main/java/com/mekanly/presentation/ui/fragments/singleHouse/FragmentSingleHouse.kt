package com.mekanly.presentation.ui.fragments.singleHouse

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekanly.R

class FragmentSingleHouse : Fragment() {

    companion object {
        fun newInstance() = FragmentSingleHouse()
    }

    private val viewModel: VMSingleHouse by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_single_house, container, false)
    }
}