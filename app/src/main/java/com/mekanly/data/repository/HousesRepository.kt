package com.mekanly.data.repository

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
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            apiService.getFilteredResult(locationId = location.id)
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
            apiService.getFilteredResult(categoryId = category.id.toString())
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
            val minRaw = priceRange.min?.takeIf { it.isNotBlank() } ?: "0"
            val maxRaw = priceRange.max?.takeIf { it.isNotBlank() }

            apiService.getFilteredResult(cheapPrice = minRaw, expensivePrice = maxRaw)
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

    fun addHouse(requestBody:AddHouseBody, callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.addHouse(requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                        callback(ResponseBodyState.Success("Success"))
                } else {
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
        const val LIMIT_REGULAR: Long = 100
    }

}