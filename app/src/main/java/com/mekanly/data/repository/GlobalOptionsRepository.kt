package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.models.ResponseData
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalOptionsRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getGlobalOptions(callback: (ResponseBodyState) -> Unit){
        apiService.globalOptions().enqueue(object : Callback<ResponseData<DataGlobalOptions>> {
            override fun onResponse(call: Call<ResponseData<DataGlobalOptions>>, response: Response<ResponseData<DataGlobalOptions>>) {
                if (response.isSuccessful) {
                    Log.e("FlowFragment", "onResponse: "+response.body().toString() )
                    val responseData = response.body()?.data
                    if (responseData!=null){
                        callback(ResponseBodyState.Success(responseData))
                    }
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseData<DataGlobalOptions>>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }
}