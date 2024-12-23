package com.mekanly.data.responseBody

sealed class ResponseBodyState {
    data class Success(val dataResponse:Any):ResponseBodyState()
    data class SuccessList(val dataResponse:List<Any>):ResponseBodyState()
    data object Loading:ResponseBodyState()
    data class Error(val error:Any):ResponseBodyState()
}