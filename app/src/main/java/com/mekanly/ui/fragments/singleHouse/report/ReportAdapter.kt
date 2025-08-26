package com.mekanly.ui.fragments.singleHouse.report

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.mekanly.R
import com.mekanly.data.models.Report

class ReportAdapter(
    private val reportList: List<Report>,
    private val onOptionSelected: (Report) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class ReportViewHolder(val radioButton: RadioButton) : RecyclerView.ViewHolder(radioButton) {
        fun bind(report: Report, isSelected: Boolean) {
            radioButton.text = report.description
            radioButton.isChecked = isSelected
            radioButton.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                if (previousPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(previousPosition)
                }
                notifyItemChanged(selectedPosition)
                onOptionSelected(report)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_option, parent, false) as RadioButton
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int = reportList.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(reportList[position], position == selectedPosition)
    }

    // Добавляем метод для получения выбранного отчета
    fun getSelectedReport(): Report? {
        return if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < reportList.size) {
            reportList[selectedPosition]
        } else {
            null
        }
    }

    // Проверка, выбран ли какой-либо отчет
    fun hasSelection(): Boolean {
        return selectedPosition != RecyclerView.NO_POSITION && selectedPosition < reportList.size
    }

    // Сброс выбора (если потребуется)
    fun clearSelection() {
        val previousPosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        if (previousPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousPosition)
        }
    }
}