package com.mekanly.utils

import android.content.Context
import com.mekanly.R

class Constants {
    companion object {
        const val NO_CONTENT = 1
        const val UNSUCCESSFUL_RESPONSE = 2
        const val RESPONSE_FAILURE = 3

        fun getErrorMessageUpToType(context: Context, errorType: Int): String {
            return when (errorType) {
                NO_CONTENT -> context.getString(R.string.error_no_content)
                UNSUCCESSFUL_RESPONSE -> context.getString(R.string.error_failure)
                RESPONSE_FAILURE -> context.getString(R.string.error_no_internet)
                else -> context.getString(R.string.error_no_content)
            }
        }

        const val OWNER = "eyesinden"
        const val REALTOR = "realtor"
        const val SORT_BY_PRICE = "price"
        const val SORT_BY_CREATED_AT = "created_at"
        const val SORT_ORDER_ASC = "asc"
        const val SORT_ORDER_DESC = "desc"
        const val FAV_TYPE_SHOP = "Shop"
        const val FAV_TYPE_HOUSE = "House"
        const val COMMENT_TYPE_HOUSE = "House"
        const val COMMENT_TYPE_PRODUCT = "Product"

    }



}