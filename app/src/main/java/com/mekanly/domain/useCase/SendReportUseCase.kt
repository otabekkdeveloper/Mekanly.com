package com.mekanly.domain.useCase

import com.mekanly.data.repository.ReportRepository
import com.mekanly.domain.model.ResponseBodyState

class SendReportUseCase {
    private val rep by lazy { ReportRepository() }

    fun sendReport(
        abuseListId: Int,
        itemId: Int,
        message: String,
        type: String,
        callback: (ResponseBodyState) -> Unit
    ) {
        rep.sendReport(abuseListId, itemId, message, type, callback)
    }
}
