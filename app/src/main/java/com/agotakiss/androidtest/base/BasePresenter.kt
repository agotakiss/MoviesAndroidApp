package com.agotakiss.androidtest.base

import android.content.ContentValues
import android.util.Log

open class BasePresenter(){

    protected val TAG = this.javaClass.simpleName

    protected fun logE(message: Any) {
        Log.e(ContentValues.TAG, message.toString())
    }
}