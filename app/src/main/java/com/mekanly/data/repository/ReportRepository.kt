package com.mekanly.data.repository

import android.util.Log
import com.mekanly.data.models.Report
import com.mekanly.data.models.ResponseDataList
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.utils.Constants.Companion.RESPONSE_FAILURE
import com.mekanly.utils.Constants.Companion.UNSUCCESSFUL_RESPONSE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportRepository {

    private val apiService = ApiClient.instance.create(ApiService::class.java)

    fun getReports(callback: (ResponseBodyState) -> Unit) {
        callback(ResponseBodyState.Loading)

        // ❗ Измени тип Call на Call<List<Report>>
        apiService.getReports().enqueue(object : Callback<ResponseDataList<Report>> {
            override fun onResponse(
                call: Call<ResponseDataList<Report>>, response: Response<ResponseDataList<Report>>
            ) {
                if (response.isSuccessful) {
                    val reports: List<Report> = response.body()?.data ?: emptyList()
                    callback(ResponseBodyState.SuccessList(reports))
                } else {
                    Log.e("ReportRepository", "Error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<ResponseDataList<Report>>, t: Throwable) {
                Log.e("ReportRepository", "Failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

    fun sendReport(
        abuseListId: Int,
        itemId: Int,
        message: String,
        type: String,
        callback: (ResponseBodyState) -> Unit
    ) {
        callback(ResponseBodyState.Loading)

        apiService.sendReport(
            abuseListId = abuseListId,
            itemId = itemId,
            message = message,
            type = type
        ).enqueue(object : Callback<okhttp3.ResponseBody> {
            override fun onResponse(
                call: Call<okhttp3.ResponseBody>,
                response: Response<okhttp3.ResponseBody>
            ) {
                if (response.isSuccessful) {
                    val responseString = response.body()?.string() ?: ""
                    callback(ResponseBodyState.Success(responseString))
                } else {
                    Log.e("ReportRepository", "sendReport error: ${response.code()}")
                    callback(ResponseBodyState.Error(UNSUCCESSFUL_RESPONSE))
                }
            }

            override fun onFailure(call: Call<okhttp3.ResponseBody>, t: Throwable) {
                Log.e("ReportRepository", "sendReport failure: ${t.message}")
                callback(ResponseBodyState.Error(RESPONSE_FAILURE))
            }
        })
    }

}
