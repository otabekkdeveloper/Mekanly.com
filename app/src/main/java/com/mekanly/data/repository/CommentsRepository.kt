package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.Comment
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getHouseComments(id:Long,callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getHouseComments(houseId = id.toString())
            .enqueue(object : Callback<ResponseDataList<Comment>> {
                override fun onResponse(
                    call: Call<ResponseDataList<Comment>>, response: Response<ResponseDataList<Comment>>
                ) {
                    if (response.isSuccessful) {
                        val comments:List<Any> = response.body()?.data ?: emptyList()
                        callback(ResponseBodyState.SuccessList(comments))
                    } else {
                        Log.e("FlowFragment", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }

                override fun onFailure(call: Call<ResponseDataList<Comment>>, t: Throwable) {
                    Log.e("FlowFragment", "Failure: ${t.message}")
                    callback(ResponseBodyState.Error(RESPONSE_FAILURE))
                }
            })
    }
}