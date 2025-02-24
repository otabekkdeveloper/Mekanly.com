package com.mekanly.domain.useCase

import android.content.Context
import com.mekanly.data.repository.RepositoryUser
import com.mekanly.data.responseBody.ResponseBodyState

class GetProfileDataUseCase {
    private val rep by lazy {
        RepositoryUser()
    }

    fun execute(context: Context, callback: (ResponseBodyState) -> Unit){
        val token:String = ""
        rep.getUserData(token,callback)
    }
}