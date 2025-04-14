package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.NO_CONTENT
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.BusinessCategory
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.repository.HousesRepository.Companion.LIMIT_REGULAR
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessProfilesRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)
    fun getBusinessProfileCategories(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getBusinessProfileCategories().enqueue(object : Callback<ResponseDataList<BusinessCategory>> {
            override fun onResponse(call: Call<ResponseDataList<BusinessCategory>>, response: Response<ResponseDataList<BusinessCategory>>) {
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

            override fun onFailure(call: Call<ResponseDataList<BusinessCategory>>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun getBusinessProfilesWithPagination(start: Long, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getBusinessProfilesWithPagination(start = start, limit = LIMIT_REGULAR)
            .enqueue(object : Callback<ResponseDataList<BusinessProfile>> {
                override fun onResponse(
                    call: Call<ResponseDataList<BusinessProfile>>, response: Response<ResponseDataList<BusinessProfile>>
                ) {
                    if (response.isSuccessful) {
                        val businessProfiles = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(businessProfiles))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<BusinessProfile>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }

    fun getSimilarBusinessProfiles(id: Long, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getSimilarBusinessProfiles(id)
            .enqueue(object : Callback<ResponseDataList<BusinessProfile>> {
                override fun onResponse(
                    call: Call<ResponseDataList<BusinessProfile>>, response: Response<ResponseDataList<BusinessProfile>>
                ) {
                    if (response.isSuccessful) {
                        val businessProfiles = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(businessProfiles))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<BusinessProfile>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }
}