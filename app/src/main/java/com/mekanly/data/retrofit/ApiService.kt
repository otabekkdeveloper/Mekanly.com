package com.mekanly.data.retrofit


import com.mekanly.data.responseBody.ResponseHouses
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v1/houses")
    fun getHouses(): Call<ResponseHouses>
}

