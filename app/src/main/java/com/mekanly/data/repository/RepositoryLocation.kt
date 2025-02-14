package com.mekanly.data.repository

import com.mekanly.data.dataModels.DataLocation
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryLocation {

    private val apiService: ApiService = ApiClient.instance.create(ApiService::class.java)

    // Метод для получения локаций с API
    fun getLocations(callback: (List<DataLocation>?) -> Unit, onFailure: (Throwable) -> Unit) {
        apiService.getLocations().enqueue(object : Callback<List<DataLocation>> {
            override fun onResponse(call: Call<List<DataLocation>>, response: Response<List<DataLocation>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    onFailure(Throwable("Response not successful"))
                }
            }

            override fun onFailure(call: Call<List<DataLocation>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}
