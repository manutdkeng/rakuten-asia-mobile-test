package com.zack.android.test.rakuten.utils

import androidx.annotation.StringRes

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: java.lang.Exception? = null,
        val message: String = "",
        @StringRes val messageId: Int = 0
    ) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message, exception= $exception]"
        }
    }
}