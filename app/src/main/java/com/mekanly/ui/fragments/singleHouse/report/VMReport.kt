package com.mekanly.ui.fragments.singleHouse.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mekanly.data.models.Report
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetReportListUseCase
import com.mekanly.domain.useCase.SendReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VMReport : ViewModel() {

    private val getReportsUseCase by lazy { GetReportListUseCase() }
    private val sendReportUseCase by lazy { SendReportUseCase() }

    private val _reportState = MutableStateFlow<ResponseBodyState>(ResponseBodyState.Loading)
    val reportState: StateFlow<ResponseBodyState> = _reportState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedReport = MutableStateFlow<Report?>(null)
    val selectedReport: StateFlow<Report?> = _selectedReport.asStateFlow()

    private val _reportList = MutableStateFlow<List<Report>>(emptyList())
    val reportList: StateFlow<List<Report>> = _reportList.asStateFlow()

    fun getReports() {
        viewModelScope.launch {
            _isLoading.value = true
            _reportState.value = ResponseBodyState.Loading

            getReportsUseCase.execute { response ->
                when (response) {
                    is ResponseBodyState.Error -> {
                        _reportState.value = ResponseBodyState.Error(response.error ?: 4)
                        _isLoading.value = false
                    }

                    is ResponseBodyState.Loading -> {
                        _reportState.value = ResponseBodyState.Loading
                        _isLoading.value = true
                    }

                    is ResponseBodyState.SuccessList -> {
                        val reports = response.dataResponse as? List<Report> ?: emptyList()
                        _reportList.value = reports
                        _reportState.value = ResponseBodyState.SuccessList(reports)
                        _isLoading.value = false
                    }

                    else -> {
                        _reportState.value = ResponseBodyState.Error(4)
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun sendReport(
        abuseListId: Int,
        itemId: Int,
        message: String,
        type: String
    ) {
        _isLoading.value = true
        _reportState.value = ResponseBodyState.Loading

        sendReportUseCase.sendReport(
            abuseListId = abuseListId,
            itemId = itemId,
            message = message,
            type = type
        ) { response ->
            _reportState.value = response
            _isLoading.value = false
        }
    }

    fun selectReport(report: Report) {
        _selectedReport.value = report
    }

    fun clearSelection() {
        _selectedReport.value = null
    }

    fun getSelectedReportInfo(): String? {
        return _selectedReport.value?.let { report ->
            "Selected: ${report.description} (ID: ${report.id}, Type: ${report.type})"
        }
    }

    fun retry() {
        getReports()
    }

    fun filterReportsByType(type: String): List<Report> {
        return _reportList.value.filter { it.type == type }
    }

    fun getReportById(id: Int): Report? {
        return _reportList.value.find { it.id == id }
    }
}
