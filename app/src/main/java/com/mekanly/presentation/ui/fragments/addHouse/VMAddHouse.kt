package com.mekanly.presentation.ui.fragments.addHouse

import androidx.lifecycle.ViewModel
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.UseCaseAddHouse

class VMAddHouse : ViewModel() {

    private val useCase by lazy {
        UseCaseAddHouse()
    }
    fun addHouse(requestBodyAddHouse: RequestBodyAddHouse,callback: (ResponseBodyState) -> Unit) {
        useCase.addHouse(requestBodyAddHouse, callback)
    }
}