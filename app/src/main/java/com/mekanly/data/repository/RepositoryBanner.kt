package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.constants.Constants.Companion.NO_CONTENT
import com.mekanly.data.constants.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.responseBody.ResponseBanners
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryBanner {
    private val apiService = ApiClient.instance.create(ApiService::class.java)
    fun getBanners(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getBanners().enqueue(object : Callback<ResponseBanners> {
            override fun onResponse(call: Call<ResponseBanners>, response: Response<ResponseBanners>) {
                if (response.isSuccessful) {
                    val banners = response.body()?.data ?: emptyList()
                    if (banners.isEmpty()) {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    } else {
                        callback(ResponseBodyState.SuccessList(banners))
                    }

                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseBanners>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }
}