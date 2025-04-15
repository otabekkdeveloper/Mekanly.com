package com.mekanly.ui.fragments.flow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.domain.useCase.GetGlobalOptionsUseCase
import com.mekanly.helpers.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VMFlow(application: Application) : AndroidViewModel(application) {

    private val _globalState = MutableStateFlow(DataGlobalOptions())
    val globalState: StateFlow<DataGlobalOptions> = _globalState.asStateFlow()

    private val useCase by lazy {
        GetGlobalOptionsUseCase()
    }

    fun getGlobalOptions() {
        useCase.execute {
            when (it) {
                is ResponseBodyState.Error -> {

                }

                is ResponseBodyState.Success -> {
                    Log.e("GLOBAL_OPTIONS", "getGlobalOptions: success " + it.dataResponse)
                    _globalState.value = it.dataResponse as DataGlobalOptions
                    saveGlobalPrefs(it.dataResponse)
                }

                else -> {}
            }
        }
    }

    private fun saveGlobalPrefs(data: DataGlobalOptions) {
        PreferencesHelper.saveGlobalOptions(data)
    }

}