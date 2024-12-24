package com.mekanly.presentation.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.R
import com.mekanly.presentation.ui.adapters.SearchImageAdapter
import com.mekanly.presentation.ui.adapters.SearchImageAdapter.Companion.LISTED_POST
import com.mekanly.presentation.ui.adapters.SearchImageAdapter.Companion.SIMPLE_POST
import com.mekanly.data.DataPost
import com.mekanly.databinding.FragmentEmlaklerBinding


class SearchSubFragment : Fragment() {
    private lateinit var binding: FragmentEmlaklerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmlaklerBinding.inflate(inflater, container, false)

        // Пример данных

        val innerList:MutableList<DataPost> = mutableListOf()
        val simplePostInHorizontal1 = DataPost(R.drawable.placeholder,SIMPLE_POST)
        val simplePostInHorizontal2 = DataPost(R.drawable.placeholder,SIMPLE_POST)
        innerList.add(simplePostInHorizontal1)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)
        innerList.add(simplePostInHorizontal2)


        val posts:MutableList<DataPost> = mutableListOf()
        val simplePost1 = DataPost(R.drawable.placeholder,SIMPLE_POST)
        val simplePost2 = DataPost(R.drawable.placeholder,SIMPLE_POST)
        val listedPost1 = DataPost(type=LISTED_POST, inner = innerList)
        
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(listedPost1)

        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(listedPost1)

        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(listedPost1)

        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(simplePost2)
        posts.add(listedPost1)

        binding.recyclerViewTwo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewTwo.adapter = SearchImageAdapter(posts)





        return binding.root
    }


}

