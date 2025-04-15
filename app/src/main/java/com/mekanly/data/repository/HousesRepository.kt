package com.mekanly.data.repository

import android.net.Uri
import android.util.Log
import com.mekanly.utils.Constants.Companion.NO_CONTENT
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.House
import com.mekanly.data.models.Location
import com.mekanly.data.models.PriceRange
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.request.AddHouseBody
import com.mekanly.data.models.HouseCategory
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.data.models.HouseDetails
import com.mekanly.data.request.FilterRequest
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            override fun onResponse(call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>) {
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
        start: Long,
        location: Location? = null,
        category: HouseCategory? = null,
        priceRange: PriceRange?=null,
        callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)
        if (location != null) {
            apiService.getFilteredResult(FilterRequest(locationId = location.id))
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
        } else if (category != null) {
            apiService.getFilteredResult(FilterRequest(categoryId = category.id))
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
        } else if (priceRange!=null) {
            Log.e("PRICE_FILTER", "getHousesPagination: ", )
            val minRaw = priceRange.min ?: 0
            val maxRaw = priceRange.max ?: 0

            apiService.getFilteredResult(FilterRequest(cheapPrice = minRaw, expensivePrice = maxRaw))
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
        } else {
            apiService.getHousesWithPagination(start = start, limit = LIMIT_REGULAR)
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

    }

    fun searchHouses(query: String, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.search(search = query)
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


    fun getHouseDetails(id:Long,callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getHouseDetails(houseId = id.toString())
            .enqueue(object : Callback<HouseDetails> {
                override fun onResponse(
                    call: Call<HouseDetails>, response: Response<HouseDetails>
                ) {
                    if (response.isSuccessful) {
                        val house = response.body()?.data ?: ""
                        callback(ResponseBodyState.Success(house))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<HouseDetails>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }


    fun getTopHouses(callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getTopHouses().enqueue(object : Callback<ResponseDataList<House>> {
            override fun onResponse(call: Call<ResponseDataList<House>>, response: Response<ResponseDataList<House>>) {
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

    fun addHouse(body: AddHouseBody, imageFiles: List<File>, callback: (ResponseBodyState) -> Unit) {
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
                it.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            }

            val imageParts = imageFiles.map { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("images[]", file.name, requestFile)
            }

            apiService.addHouse(
                name, description, price, locationId, categoryId, who,
                area, writeComment, floorNumber, roomNumber, exclusive,
                hashtag, levelNumber, propertyTypeId, repairTypeId,
                /*possibilities,*/ imageParts
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
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

    companion object {
        const val LIMIT_REGULAR: Long = 100
    }

    fun String.toRequestBodyOrNull(): RequestBody? =
        if (this.isNotBlank()) this.toRequestBody("text/plain".toMediaTypeOrNull()) else null

    fun Int?.toRequestBodyOrNull(): RequestBody? =
        this?.toString()?.toRequestBodyOrNull()
}