package com.agotakiss.movie4u.base

import timber.log.Timber

open class BasePresenter {

    protected val TAG = this.javaClass.simpleName

    protected fun logD(message: Any) {
        Timber.d(TAG, message.toString())
    }

    protected fun logE(message: Any) {
        Timber.e(TAG, message.toString())
    }
}