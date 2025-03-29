package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.constants.Constants.Companion.NO_CONTENT
import com.mekanly.data.constants.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.repository.RepositoryHouses.Companion.LIMIT_REGULAR
import com.mekanly.data.responseBody.ResponseBanners
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseBusinessProfiles
import com.mekanly.data.responseBody.ResponseBusinessProfilesCategories
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryBusinessProfiles {
    private val apiService = ApiClient.instance.create(ApiService::class.java)
    fun getBusinessProfileCategories(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getBusinessProfileCategories().enqueue(object : Callback<ResponseBusinessProfilesCategories> {
            override fun onResponse(call: Call<ResponseBusinessProfilesCategories>, response: Response<ResponseBusinessProfilesCategories>) {
                if (response.isSuccessful) {
                    val categories = response.body()?.data ?: emptyList()
                    if (categories.isEmpty()) {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    } else {
                        callback(ResponseBodyState.SuccessList(categories))
                    }

                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseBusinessProfilesCategories>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun getBusinessProfilesWithPagination(start: Long, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getBusinessProfilesWithPagination(start = start, limit = LIMIT_REGULAR)
            .enqueue(object : Callback<ResponseBusinessProfiles> {
                override fun onResponse(
                    call: Call<ResponseBusinessProfiles>, response: Response<ResponseBusinessProfiles>
                ) {
                    if (response.isSuccessful) {
                        val businessProfiles = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(businessProfiles))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseBusinessProfiles>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }
}