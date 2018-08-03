package com.agotakiss.androidtest.base

import android.content.ContentValues
import android.util.Log

open class BasePresenter(){

    protected val TAG = this.javaClass.simpleName

    protected fun logD(message: Any) {
        Log.d(TAG, message.toString())
    }
    protected fun logE(message: Any) {
        Log.e(TAG, message.toString())
    }
}