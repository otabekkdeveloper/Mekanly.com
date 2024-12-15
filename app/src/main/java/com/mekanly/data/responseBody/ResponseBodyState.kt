package com.mekanly.data.responseBody

sealed class ResponseBodyState {
    data class Success(val dataResponse:Any):ResponseBodyState( )
}