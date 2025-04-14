package com.mekanly.data.repository

import android.util.Log
import com.mekanly.utils.Constants.Companion.NO_CONTENT
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import com.mekanly.data.request.ConfirmationBody
import com.mekanly.data.request.AuthBody
import com.mekanly.data.responseBody.ResponseBodyState
import com.mekanly.data.responseBody.ResponseLogin
import com.mekanly.data.responseBody.ResponseRegister
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {
    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun register(requestBodyRegister: AuthBody,callback: (ResponseBodyState) -> Unit) {
        apiService.register(requestBodyRegister).enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                if (response.isSuccessful) {
                    val responseBody =  response.body()
                    if (responseBody!=null) {
                        callback(ResponseBodyState.Success(responseBody))
                    } else {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    }
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun login(requestBodyLogin: AuthBody, callback: (ResponseBodyState) -> Unit) {
        apiService.login(requestBodyLogin).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.user
                    if (responseBody != null) {
                        Log.d("LoginRepository", "Login successful: ${responseBody}")
                        callback(ResponseBodyState.Success(responseBody))
                    } else {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    }
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e("LoginRepository", "Error: ${response.code()} - $errorBody")
                        callback(ResponseBodyState.Error(errorBody ?: UNSUCCESSFUL_RESPONSE))
                    } catch (e: Exception) {
                        Log.e("LoginRepository", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("LoginRepository", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }


    fun confirmLogin(confirmationBody: ConfirmationBody, callback: (ResponseBodyState) -> Unit) {
        apiService.confirmLogin(confirmationBody).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.user
                    if (responseBody != null) {
                        Log.d("LoginRepository", "Login successful: ${responseBody}")
                        callback(ResponseBodyState.Success(responseBody))
                    } else {
                        callback(ResponseBodyState.Error(NO_CONTENT))
                    }
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e("LoginRepository", "Error: ${response.code()} - $errorBody")
                        callback(ResponseBodyState.Error(errorBody ?: UNSUCCESSFUL_RESPONSE))
                    } catch (e: Exception) {
                        Log.e("LoginRepository", "Error: ${response.code()}")
                        callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("LoginRepository", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

}