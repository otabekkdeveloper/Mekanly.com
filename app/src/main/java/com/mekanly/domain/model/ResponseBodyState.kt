package com.mekanly.domain.model

sealed class ResponseBodyState {
    data class Success(val dataResponse:Any): ResponseBodyState()
    data class SuccessList(val dataResponse:List<Any>): ResponseBodyState()
    data object Loading: ResponseBodyState()
    data object Initial: ResponseBodyState()
    data class Error(val error:Any): ResponseBodyState()
}