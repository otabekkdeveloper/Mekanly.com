package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.models.FavoritesRequest
import com.mekanly.data.models.FavoriteHousesResponse
import com.mekanly.data.models.FavoriteProductsResponse
import com.mekanly.utils.Constants.Companion.NO_CONTENT
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.House
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.request.AddHouseBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.data.models.HouseDetails
import com.mekanly.data.models.ResponseData
import com.mekanly.data.models.TopHouses
import com.mekanly.data.request.FilterBody
import com.mekanly.data.request.ReactionBody
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class HousesRepository {

    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getHouses(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getHouses().enqueue(object : Callback<ResponseDataList<House>> {
            override fun onResponse(
                call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>
            ) {
                if (response.isSuccessful) {
                    val houses = response.body()?.data ?: emptyList()
                    if (houses.isEmpty()) {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    } else {
                        callback(ResponseBodyState.SuccessList(houses))
                    }

                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseDataList<House>>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun getHousesPagination(
        filterBody: FilterBody, callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)
        apiService.getFilteredResult(filterBody)
            .enqueue(object : Callback<ResponseDataList<House>> {
                override fun onResponse(
                    call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>
                ) {
                    if (response.isSuccessful) {
                        val houses = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(houses))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<House>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })

    }

    fun searchHouses(query: String, start: Int, limit: Int, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.search(search = query, start = start, limit = limit)
            .enqueue(object : Callback<ResponseDataList<House>> {
                override fun onResponse(
                    call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>
                ) {
                    if (response.isSuccessful) {
                        val houses = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(houses))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<House>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }


    fun getHouseDetails(id: Long, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getHouseDetails(houseId = id.toString())
            .enqueue(object : Callback<ResponseData<HouseDetails>> {
                override fun onResponse(
                    call: Call<ResponseData<HouseDetails>>,
                    response: Response<ResponseData<HouseDetails>>
                ) {
                    if (response.isSuccessful) {
                        val house = response.body()?.data ?: ""
                        callback(ResponseBodyState.Success(house))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseData<HouseDetails>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }


    fun getTopHouses(start: Int, limit: Int, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getTopHouses(start, limit)
            .enqueue(object : Callback<ResponseDataList<TopHouses>> {
                override fun onResponse(
                    call: Call<ResponseDataList<TopHouses>>,
                    response: Response<ResponseDataList<TopHouses>>
                ) {
                    if (response.isSuccessful) {
                        val houses = response.body()?.data ?: emptyList()
                        if (houses.isEmpty()) {
                            callback(ResponseBodyState.Error(NO_CONTENT))
                        } else {
                            callback(ResponseBodyState.SuccessList(houses))
                        }
                    } else {
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<TopHouses>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }

    fun addHouse(
        body: AddHouseBody, imageFiles: List<File>, callback: (ResponseBodyState) -> Unit
    ) {
        try {
            val mediaTypeText = "text/plain".toMediaTypeOrNull()
            val name = body.name?.toRequestBody(mediaTypeText)
            val description = body.description?.toRequestBody(mediaTypeText)
            val price = body.price?.toString()?.toRequestBody(mediaTypeText)
            val locationId = body.locationId?.toString()?.toRequestBody(mediaTypeText)
            val categoryId = body.categoryId?.toString()?.toRequestBody(mediaTypeText)
            val who = body.who?.toRequestBody(mediaTypeText)
            val area = body.area?.toString()?.toRequestBody(mediaTypeText)
            val writeComment = body.writeComment?.toString()?.toRequestBody(mediaTypeText)
            val floorNumber = body.floorNumber?.toString()?.toRequestBody(mediaTypeText)
            val roomNumber = body.roomNumber?.toString()?.toRequestBody(mediaTypeText)
            val exclusive = body.exclusive?.toString()?.toRequestBody(mediaTypeText)
            val hashtag = body.hashtag?.toRequestBody(mediaTypeText)
            val levelNumber = body.levelNumber?.toString()?.toRequestBody(mediaTypeText)
            val propertyTypeId = body.propertyTypeId?.toString()?.toRequestBody(mediaTypeText)
            val repairTypeId = body.repairTypeId?.toString()?.toRequestBody(mediaTypeText)

            val possibilities = body.possibilities?.map {
                it.toString()
            }.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imageParts = imageFiles.map { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image[]", file.name, requestFile)
            }

            apiService.addHouse(
                name,
                description,
                price,
                locationId,
                categoryId,
                who,
                area,
                writeComment,
                floorNumber,
                roomNumber,
                exclusive,
                hashtag,
                levelNumber,
                propertyTypeId,
                repairTypeId,
                possibilities,
                imageParts
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        callback(ResponseBodyState.Success("Success"))
                    } else {
                        callback(ResponseBodyState.Error("Unsuccessful response"))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback(ResponseBodyState.Error(t.message ?: "Unknown failure"))
                }
            })

        } catch (e: Exception) {
            callback(ResponseBodyState.Error(e.message ?: "Unknown error"))
        }
    }

    fun getUserHouses(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getUserHouses().enqueue(object : Callback<ResponseDataList<House>> {
            override fun onResponse(
                call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>
            ) {
                if (response.isSuccessful) {
                    val houses = response.body()?.data ?: emptyList()
                    if (houses.isEmpty()) {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    } else {
                        callback(ResponseBodyState.SuccessList(houses))
                    }
                } else {
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseDataList<House>>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun getFavoriteHouses(
        request: FavoritesRequest, callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)

        apiService.getFavoriteHouses(request).enqueue(object : Callback<FavoriteHousesResponse> {
            override fun onResponse(
                call: Call<FavoriteHousesResponse>, response: Response<FavoriteHousesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(ResponseBodyState.SuccessList(responseBody.data))
                    } else {
                        callback(ResponseBodyState.Error("Empty response body"))
                    }
                } else {
                    Log.e("FavoritesRepo", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error("Unsuccessful response: ${response.code()}"))
                }
            }


            override fun onFailure(call: Call<FavoriteHousesResponse>, t: Throwable) {
                Log.e("FavoritesRepo", "Failure: ${t.message}")
                callback(ResponseBodyState.Error("Failure: ${t.message}"))
            }
        })
    }


    fun getFavoriteProducts(
        request: FavoritesRequest, callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)

        apiService.getFavoriteProducts(request).enqueue(object : Callback<FavoriteProductsResponse> {
            override fun onResponse(
                call: Call<FavoriteProductsResponse>, response: Response<FavoriteProductsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(ResponseBodyState.SuccessList(responseBody.data))
                    } else {
                        callback(ResponseBodyState.Error("Empty response body"))
                    }
                } else {
                    Log.e("FavoritesRepo", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error("Unsuccessful response: ${response.code()}"))
                }
            }


            override fun onFailure(call: Call<FavoriteProductsResponse>, t: Throwable) {
                Log.e("FavoritesRepo", "Failure: ${t.message}")
                callback(ResponseBodyState.Error("Failure: ${t.message}"))
            }
        })
    }

    fun toggleFavorite(request: ReactionBody, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.toggleFavorite(request).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    callback(ResponseBodyState.Success("Success"))
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }


        })
    }


    companion object {
        const val LIMIT_REGULAR: Long = 25
    }

}