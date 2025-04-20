package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.models.Comment
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.request.AddCommentBody
import com.mekanly.data.request.UpdateCommentBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getComments(id:Long, type:String, start:Int, limit:Int = 10, callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.getComments(start, limit, id, type)
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

    fun addComment(addCommentBody: AddCommentBody, callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.addComment(addCommentBody)
            .enqueue(object : Callback<ResponseBody> {
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

    fun updateComment(id:Long, updateCommentBody: UpdateCommentBody, callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.updateComment(id, updateCommentBody)
            .enqueue(object : Callback<ResponseBody> {
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

    fun deleteComment(id:Long, callback: (ResponseBodyState) -> Unit){
        callback(ResponseBodyState.Loading)
        apiService.deleteComment(id)
            .enqueue(object : Callback<ResponseBody> {
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
}