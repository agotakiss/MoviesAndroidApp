package com.agotakiss.movie4u.base

import android.util.Log

open class BasePresenter() {

    protected val TAG = this.javaClass.simpleName

    protected fun logD(message: Any) {
        Log.d(TAG, message.toString())
    }
    protected fun logE(message: Any) {
        Log.e(TAG, message.toString())
    }
}