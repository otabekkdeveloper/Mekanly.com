package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryHouses {

    val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getHouses(){
        apiService.getHouses().enqueue(object : Callback<ResponseHouses> {
            override fun onResponse(call: Call<ResponseHouses>, response: Response<ResponseHouses>) {
                if (response.isSuccessful) {
                    val properties = response.body()?.data ?: emptyList()
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
            }
        })
    }

}