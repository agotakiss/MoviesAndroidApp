package com.agotakiss.androidtest.presentation

import android.app.Activity
import android.content.ContentValues.TAG
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.agotakiss.androidtest.base.MovieApplication

abstract class BaseActivity : AppCompatActivity() {

    val Activity.movieApplication: MovieApplication get() = application as MovieApplication

    protected fun logE(message: Any) {
        Log.e(TAG, message.toString())
    }

    protected fun logD(message: String) {
        Log.d(TAG, message)
    }
}
