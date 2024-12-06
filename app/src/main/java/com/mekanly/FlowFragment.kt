package com.mekanly


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekanly.adapters.PropertyAdapter
import com.mekanly.data.responseLocation.ApiResponse
import com.mekanly.data.retrofit.ApiClient
import com.mekanly.data.retrofit.ApiService
import com.mekanly.databinding.FragmentFlowBinding


class FlowFragment : Fragment() {
    private lateinit var binding: FragmentFlowBinding
    private lateinit var propertyAdapter: PropertyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)


        // Настройка RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val apiService = ApiClient.instance.create(ApiService::class.java)
        apiService.getHouses().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val properties = response.body()?.data ?: emptyList()
                    propertyAdapter = PropertyAdapter(properties)
                    binding.recyclerView.adapter = propertyAdapter
                } else {
                    Log.e("FlowFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("FlowFragment", "Failure: ${t.message}")
            }
        })

        return binding.root
    }


    }
