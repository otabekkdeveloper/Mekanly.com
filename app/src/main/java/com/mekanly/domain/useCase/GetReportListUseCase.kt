package com.mekanly.domain.useCase

import com.mekanly.data.repository.ReportRepository
import com.mekanly.domain.model.ResponseBodyState

class GetReportListUseCase {

    // Ленивая инициализация репозитория
    private val rep by lazy { ReportRepository() }

    // Выполняем запрос, возвращаем результат через callback
    fun execute(callback: (ResponseBodyState) -> Unit) {
        rep.getReports(callback)
    }
}
