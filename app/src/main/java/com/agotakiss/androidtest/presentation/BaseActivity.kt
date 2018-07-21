package com.agotakiss.androidtest.presentation

import android.app.Activity
import android.util.Log

open class BaseActivity : Activity() {
    protected val TAG = this.javaClass.simpleName


    protected fun logE(message: Any) {
        Log.e(TAG, message.toString())
    }

    protected fun logD(message: String) {
        Log.d(TAG, message)
    }
}
