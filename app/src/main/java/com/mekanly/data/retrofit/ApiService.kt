package com.mekanly.data.retrofit


import com.mekanly.data.dataModels.DataUser
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.requestBody.RequestBodyConfirmation
import com.mekanly.data.requestBody.RequestBodyLogin
import com.mekanly.data.requestBody.RequestBodyRegister
import com.mekanly.data.responseBody.ResponseBanners
import com.mekanly.data.responseBody.ResponseBusinessProfiles
import com.mekanly.data.responseBody.ResponseBusinessProfilesCategories
import com.mekanly.data.responseBody.ResponseComments
import com.mekanly.data.responseBody.ResponseGlobalOptions
import com.mekanly.data.responseBody.ResponseHouseDetails
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.responseBody.ResponseLogin
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
    ): Call<ResponseLogin>


    @POST("/api/checkLogin")
    fun confirmLogin(
        @Body requestBody: RequestBodyConfirmation
    ): Call<ResponseLogin>

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

    @GET("api/v2/business/categories")
    fun getBusinessProfileCategories():Call<ResponseBusinessProfilesCategories>

    @GET("api/v2/business/categories/{id}/profiles")
    fun getSimilarBusinessProfiles(
        @Path("id") id:Long
    ):Call<ResponseBusinessProfiles>

    @GET("api/v2/business/allProfiles/{start}/{limit}")
    fun getBusinessProfilesWithPagination(
        @Path("start") start:Long,
        @Path("limit") limit:Long,
    ):Call<ResponseBusinessProfiles>

    @GET("api/v2/business/categories/{id}")
    fun getSimilarBusinessProfileCategories(
        @Path("id") id:Long
    ):Call<ResponseBusinessProfilesCategories>


    @GET("api/v2/filter")
    fun getFilteredResult(
        @Query("category_id") categoryId: String? = null,
        @Query("location_id") locationId: Int? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("possibilities") possibilities: String? = null,
        @Query("image") image: String? = null,
        @Query("who") who: String? = null,
        @Query("cheap_price") cheapPrice: String? = null,
        @Query("expensive_price") expensivePrice: String? = null,
        @Query("small_area") smallArea: String? = null,
        @Query("big_area") bigArea: String? = null,
        @Query("location") location: String? = null, // JSON format
        @Query("categories") categories: String? = null, // JSON format
        @Query("property_type") propertyType: String? = null, // JSON format
        @Query("repair_type") repairType: String? = null, // JSON format
        @Query("status") status: String? = null,
        @Query("room_number") roomNumber: String? = null, // JSON format
        @Query("floor_number") floorNumber: String? = null, // JSON format
        @Query("sort_by") sortBy: String? = null, // "price", "created_at"
        @Query("sort_order") sortOrder: String? = null, // "asc", "desc"
        @Query("limit") limit: String? = null, // Example: "15"
        @Query("offset") offset: String? = null // Example: "0"
    ): Call<ResponseHouses>
}

