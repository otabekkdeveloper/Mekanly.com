package com.mekanly.data

data class DataPost(
     val imageItem:Int?=null,
     val type:Int,
     val inner:List<DataPost>?=null
)
