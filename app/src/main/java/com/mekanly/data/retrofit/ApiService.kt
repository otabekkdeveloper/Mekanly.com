package com.mekanly.data.retrofit


import com.mekanly.data.dataModels.DataUser
import com.mekanly.data.requestBody.RequestBodyLogin
import com.mekanly.data.requestBody.RequestBodyRegister
import com.mekanly.data.responseBody.ResponseBanners
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.responseBody.ResponseRegister
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/houses")
    fun getHouses(): Call<ResponseHouses>

    @GET("api/v1/houses/{start}/{limit}")
    fun getHousesWithPagination(
        @Path("start") start:Long,
        @Path("limit") limit:Long,
    ): Call<ResponseHouses>

    @GET("api/v1/search")
    fun search(
        @Query("search") search:String
    ):Call<ResponseHouses>

    @GET("api/v1/profile")
    fun getProfileData(
        @Header("Authorization") token: String
    ): Call<DataUser>

    @POST("/api/register")
    fun register(
        @Body requestBodyRegister: RequestBodyRegister
    ): Call<ResponseRegister>


    @POST("/api/login")
    fun login(
        @Body requestBody: RequestBodyLogin
    ): Call<ResponseBody>

    @GET("/api/v1/banners")
    fun getBanners(): Call<ResponseBanners>

}

