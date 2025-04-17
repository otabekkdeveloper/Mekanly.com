package com.mekanly.data.retrofit


import com.mekanly.data.models.Banner
import com.mekanly.data.models.BusinessCategory
import com.mekanly.data.models.BusinessProfile
import com.mekanly.data.models.Comment
import com.mekanly.data.models.DataGlobalOptions
import com.mekanly.data.models.House
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.models.User
import com.mekanly.data.request.AddHouseBody
import com.mekanly.data.request.ConfirmationBody
import com.mekanly.data.request.AuthBody
import com.mekanly.data.models.HouseDetails
import com.mekanly.data.models.ResponseData
import com.mekanly.data.request.FilterBody
import com.mekanly.data.responseBody.ResponseLogin
import com.mekanly.data.responseBody.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/houses")
    fun getHouses(): Call<ResponseDataList<House>>

    @GET("api/v1/houses/{start}/{limit}")
    fun getHousesWithPagination(
        @Path("start") start:Long,
        @Path("limit") limit:Long,
    ): Call<ResponseDataList<House>>

    @GET("api/v1/search")
    fun search(
        @Query("search") search:String
    ):Call<ResponseDataList<House>>

    @GET("api/v1/profile")
    fun getProfileData(
        @Header("Authorization") token: String
    ): Call<User>

    @POST("/api/register")
    fun register(
        @Body requestBodyRegister: AuthBody
    ): Call<ResponseRegister>


    @POST("/api/login")
    fun login(
        @Body requestBody: AuthBody
    ): Call<ResponseLogin>


    @POST("/api/checkLogin")
    fun confirmLogin(
        @Body requestBody: ConfirmationBody
    ): Call<ResponseLogin>

    @GET("/api/v2/banners")
    fun getBanners(): Call<ResponseDataList<Banner>>

    @GET("/api/v2/house/{house_id}")
    fun getHouseDetails(
        @Path("house_id") houseId:String
    ):Call<ResponseData<HouseDetails>>

    @GET("/api/v2/top")
    fun getTopHouses():Call<ResponseDataList<House>>

    @GET("/api/v1/houses/{houseId}/comments")
    fun getHouseComments(
        @Path("houseId") houseId:String
    ):Call<ResponseDataList<Comment>>

    @POST("/api/v1/houses/{house_id}/update")
    fun updateHouse(
        @Path("house_id") houseId:String,
        @Body addHouseBody: AddHouseBody
    ):Call<ResponseBody>

    @GET("/api/v2/globalOptions")
    fun globalOptions():Call<ResponseData<DataGlobalOptions>>

    @GET("api/v2/business/categories")
    fun getBusinessProfileCategories():Call<ResponseDataList<BusinessCategory>>

    @GET("api/v2/business/categories/{id}/profiles")
    fun getSimilarBusinessProfiles(
        @Path("id") id:Long
    ):Call<ResponseDataList<BusinessProfile>>

    @GET("api/v2/business/allProfiles/{start}/{limit}")
    fun getBusinessProfilesWithPagination(
        @Path("start") start:Long,
        @Path("limit") limit:Long,
    ):Call<ResponseDataList<BusinessProfile>>

    @GET("api/v2/business/categories/{id}")
    fun getSimilarBusinessProfileCategories(
        @Path("id") id:Long
    ):Call<ResponseDataList<BusinessCategory>>


    @POST("api/v2/filter")
    fun getFilteredResult(
        @Body request: FilterBody
    ): Call<ResponseDataList<House>>

    @Multipart
    @POST("api/v2/houses/add")
    fun addHouse(
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("location_id") locationId: RequestBody?,
        @Part("category_id") categoryId: RequestBody?,
        @Part("who") who: RequestBody?,
        @Part("area") area: RequestBody?,
        @Part("write_comment") writeComment: RequestBody?,
        @Part("floor_number") floorNumber: RequestBody?,
        @Part("room_number") roomNumber: RequestBody?,
        @Part("exclusive") exclusive: RequestBody?,
        @Part("hashtag") hashtag: RequestBody?,
        @Part("level_number") levelNumber: RequestBody?,
        @Part("property_type_id") propertyTypeId: RequestBody?,
        @Part("repair_type_id") repairTypeId: RequestBody?,
        @Part("possibilities") possibilities: RequestBody?,
        @Part images: List<MultipartBody.Part>
    ):Call<ResponseBody>
}

