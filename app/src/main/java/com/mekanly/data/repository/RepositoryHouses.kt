package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.constants.Constants.Companion.NO_CONTENT
import com.mekanly.data.constants.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.data.constants.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.dataModels.DataPriceRange
import com.mekanly.data.requestBody.RequestBodyAddHouse
import com.mekanly.data.responseBody.DataHouseCategory
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseHouseDetails
import com.mekanly.data.responseBody.ResponseHouses
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryHouses {

    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getHouses(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.getHouses().enqueue(object : Callback<ResponseHouses> {
            override fun onResponse(call: Call<ResponseHouses>, response: Response<ResponseHouses>) {
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

            override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun getHousesPagination(
        start: Long,
        location: DataLocation? = null,
        category: DataHouseCategory? = null,
        priceRange: DataPriceRange?=null,
        callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)
        if (location != null) {
            apiService.getFilteredResult(locationId = location.id)
                .enqueue(object : Callback<ResponseHouses> {
                    override fun onResponse(
                        call: Call<ResponseHouses>, response: Response<ResponseHouses>
                    ) {
                        if (response.isSuccessful) {
                            val houses = response.body()?.data ?: emptyList()
                            callback(ResponseBodyState.SuccessList(houses))
                        } else {
                            Log.e("FlowFragment", "Error: ${response.code()}")
                            callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                        }
                    }

                    override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                        Log.e("FlowFragment", "Failure: ${t.message}")
                        callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                    }
                })
        } else if (category != null) {
            apiService.getFilteredResult(categoryId = category.id.toString())
                .enqueue(object : Callback<ResponseHouses> {
                    override fun onResponse(
                        call: Call<ResponseHouses>, response: Response<ResponseHouses>
                    ) {
                        if (response.isSuccessful) {
                            val houses = response.body()?.data ?: emptyList()
                            callback(ResponseBodyState.SuccessList(houses))
                        } else {
                            Log.e("FlowFragment", "Error: ${response.code()}")
                            callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                        }
                    }

                    override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                        Log.e("FlowFragment", "Failure: ${t.message}")
                        callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                    }
                })
        } else if (priceRange!=null) {
            Log.e("PRICE_FILTER", "getHousesPagination: ", )
            val minRaw = priceRange.min?.takeIf { it.isNotBlank() } ?: "0"
            val maxRaw = priceRange.max?.takeIf { it.isNotBlank() }

            apiService.getFilteredResult(cheapPrice = minRaw, expensivePrice = maxRaw)
                .enqueue(object : Callback<ResponseHouses> {
                    override fun onResponse(
                        call: Call<ResponseHouses>, response: Response<ResponseHouses>
                    ) {
                        if (response.isSuccessful) {
                            val houses = response.body()?.data ?: emptyList()
                            callback(ResponseBodyState.SuccessList(houses))
                        } else {
                            Log.e("FlowFragment", "Error: ${response.code()}")
                            callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                        }
                    }

                    override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                        Log.e("FlowFragment", "Failure: ${t.message}")
                        callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                    }
                })
        } else {
            apiService.getHousesWithPagination(start = start, limit = LIMIT_REGULAR)
                .enqueue(object : Callback<ResponseHouses> {
                    override fun onResponse(
                        call: Call<ResponseHouses>, response: Response<ResponseHouses>
                    ) {
                        if (response.isSuccessful) {
                            val houses = response.body()?.data ?: emptyList()
                            callback(ResponseBodyState.SuccessList(houses))
                        } else {
                            Log.e("FlowFragment", "Error: ${response.code()}")
                            callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                        }
                    }

                    override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                        Log.e("FlowFragment", "Failure: ${t.message}")
                        callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                    }
                })
        }

    }

    fun searchHouses(query: String, callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)
        apiService.search(search = query)
            .enqueue(object : Callback<ResponseHouses> {
                override fun onResponse(
                    call: Call<ResponseHouses>, response: Response<ResponseHouses>
                ) {
                    if (response.isSuccessful) {
                        val houses = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(houses))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }


    fun getHouseDetails(id:Long,callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getHouseDetails(houseId = id.toString())
            .enqueue(object : Callback<ResponseHouseDetails> {
                override fun onResponse(
                    call: Call<ResponseHouseDetails>, response: Response<ResponseHouseDetails>
                ) {
                    if (response.isSuccessful) {
                        val house = response.body()?.data ?: ""
                        callback(ResponseBodyState.Success(house))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseHouseDetails>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }


    fun getTopHouses(callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getTopHouses().enqueue(object : Callback<ResponseHouses> {
            override fun onResponse(call: Call<ResponseHouses>, response: Response<ResponseHouses>) {
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
            override fun onFailure(call: Call<ResponseHouses>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun addHouse(requestBody:RequestBodyAddHouse, callback: (ResponseBodyState) -> Unit){
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