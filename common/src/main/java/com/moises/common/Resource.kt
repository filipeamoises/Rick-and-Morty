package com.moises.common

import androidx.annotation.StringRes

sealed class Resource<T>(val data: T? = null, val message: String? = null, @StringRes val messageRes: Int? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(message: String = "", @StringRes messageRes: Int? = null) : Resource<T>(message = message, messageRes = messageRes)
    class Loading<T> : Resource<T>()
}
