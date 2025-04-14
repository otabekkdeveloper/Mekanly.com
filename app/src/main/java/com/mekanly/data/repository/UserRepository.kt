package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.NO_CONTENT
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.User
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getUserData(token:String,callback: (ResponseBodyState) -> Unit) {
        apiService.getProfileData(token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
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

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

}