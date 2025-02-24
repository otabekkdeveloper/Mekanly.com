package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.constants.Constants.Companion.NO_CONTENT
import com.mekanly.data.constants.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.dataModels.DataUser
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryUser {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getUserData(token:String,callback: (ResponseBodyState) -> Unit) {
        apiService.getProfileData(token).enqueue(object : Callback<DataUser> {
            override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                if (response.isSuccessful) {
                   val profile =  response.body()
                    if (profile!=null) {
                        callback(ResponseBodyState.Success(profile))
                    } else {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    }
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

}