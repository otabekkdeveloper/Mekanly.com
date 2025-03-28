package com.mekanly.data.retrofit


import com.mekanly.data.dataModels.DataUser
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.requestBody.RequestBodyLogin
import com.mekanly.data.requestBody.RequestBodyRegister
import com.mekanly.data.responseBody.ResponseBanners
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseComments
import com.mekanly.data.responseBody.ResponseGlobalOptions
import com.mekanly.data.responseBody.ResponseHouseDetails
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

    @GET("/api/v2/banners")
    fun getBanners(): Call<ResponseBanners>

    @GET("/api/v2/house/{house_id}")
    fun getHouseDetails(
        @Path("house_id") houseId:String
    ):Call<ResponseHouseDetails>

    @GET("/api/v2/top")
    fun getTopHouses():Call<ResponseHouses>

    @GET("/api/v1/houses/{houseId}/comments")
    fun getHouseComments(
        @Path("houseId") houseId:String
    ):Call<ResponseComments>

    @POST("/api/v1/houses/add")
    fun addHouse(
        @Body requestBodyAddHouse: RequestBodyAddHouse
    ):Call<ResponseBody>

    @POST("/api/v1/houses/{house_id}/update")
    fun updateHouse(
        @Path("house_id") houseId:String,
        @Body requestBodyAddHouse: RequestBodyAddHouse
    ):Call<ResponseBody>

    @GET("/api/v2/globalOptions")
    fun globalOptions():Call<ResponseGlobalOptions>
}

