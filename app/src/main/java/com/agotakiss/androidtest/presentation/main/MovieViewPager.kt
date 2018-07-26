package com.agotakiss.androidtest.presentation.main

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MovieViewPager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    var shouldSkipHorizontalSwipe = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (shouldSkipHorizontalSwipe) return false
        return super.onInterceptTouchEvent(ev)
    }
}