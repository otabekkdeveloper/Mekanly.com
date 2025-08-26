package com.mekanly.data.repository

import com.mekanly.data.models.Location
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService

class LocationRepository {

    private val apiService: ApiService = ApiClient.instance.create(ApiService::class.java)

    //TODO: Otabek shu yerni gor duzat. (onFailure: (Throwable) -> Unit) koyylmidi, kyzyklanyp gor biraz.
    fun getLocations(callback: (List<Location>?) -> Unit, onFailure: (Throwable) -> Unit) {
//        apiService.getLocations().enqueue(object : Callback<List<DataLocation>> {
//            override fun onResponse(call: Call<List<DataLocation>>, response: Response<List<DataLocation>>) {
//                if (response.isSuccessful) {
//                    callback(response.body())
//                } else {
//                    onFailure(Throwable("Response not successful"))
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataLocation>>, t: Throwable) {
//                onFailure(t)
//            }
//        })
    }
}
