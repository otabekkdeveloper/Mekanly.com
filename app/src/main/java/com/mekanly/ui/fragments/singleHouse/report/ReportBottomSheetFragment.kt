package com.mekanly.ui.fragments.singleHouse.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.data.models.Report
import com.mekanly.databinding.FragmentReportBottomSheetBinding

class ReportBottomSheetFragment(
    private val report: List<Report>,
    private var onReportSelected: (Report) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReportBottomSheetBinding
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReportAdapter(report) { _ ->
            // При выборе опции только обновляем состояние кнопки
            updateConfirmButtonState()
        }

        binding.reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reportRecyclerView.adapter = adapter

        // Изначально кнопка неактивна
        updateConfirmButtonState()

        // Обработчик для кнопки подтверждения
        binding.btnConfirm.setOnClickListener {
            adapter.getSelectedReport()?.let { selectedReport ->
                onReportSelected(selectedReport)
                dismiss()
            }
        }
    }

    private fun updateConfirmButtonState() {
        binding.btnConfirm.isEnabled = adapter.hasSelection()
    }
}