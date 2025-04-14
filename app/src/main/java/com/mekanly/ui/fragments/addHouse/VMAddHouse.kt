package com.mekanly.presentation.ui.fragments.addHouse

import androidx.lifecycle.ViewModel
import com.mekanly.data.request.AddHouseBody
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.domain.useCase.AddHouseUseCase

class VMAddHouse : ViewModel() {

    private val useCase by lazy {
        AddHouseUseCase()
    }
    fun addHouse(addHouseBody: AddHouseBody, callback: (ResponseBodyState) -> Unit) {
        useCase.addHouse(addHouseBody, callback)
    }
}