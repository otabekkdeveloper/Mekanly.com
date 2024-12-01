package com.mekanly.data.retrofit


import com.mekanly.data.responseLocation.ApiResponse
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Callback
import retrofit2.Response



interface ApiService {
    @GET("api/v1/houses")
    fun getHouses(): ApiResponse
}
