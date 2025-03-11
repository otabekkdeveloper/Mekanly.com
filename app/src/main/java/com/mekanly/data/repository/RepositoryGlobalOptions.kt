package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.constants.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseGlobalOptions
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryGlobalOptions {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getGlobalOptions(callback: (ResponseBodyState) -> Unit){
        apiService.globalOptions().enqueue(object : Callback<ResponseGlobalOptions> {
            override fun onResponse(call: Call<ResponseGlobalOptions>, response: Response<ResponseGlobalOptions>) {
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

            override fun onFailure(call: Call<ResponseGlobalOptions>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }
}