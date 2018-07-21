package com.agotakiss.androidtest.data.network

interface Callback<T> {
    fun onSuccess(data: T)
    fun onError(t: Throwable)
}
