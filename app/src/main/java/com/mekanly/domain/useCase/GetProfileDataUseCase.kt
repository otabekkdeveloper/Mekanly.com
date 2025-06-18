package com.mekanly.domain.useCase

import android.content.Context
import com.mekanly.data.repository.UserRepository
import com.mekanly.domain.model.ResponseBodyState

class GetProfileDataUseCase {
    private val rep by lazy {
        UserRepository()
    }

    fun execute(context: Context, callback: (ResponseBodyState) -> Unit){
        val token:String = ""
        rep.getUserData(token,callback)
    }
}