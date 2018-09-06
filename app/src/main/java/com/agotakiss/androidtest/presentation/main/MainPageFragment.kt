package com.agotakiss.androidtest.presentation.main

import android.support.v4.app.Fragment

abstract class MainPageFragment : Fragment() {

    open fun onPageShow() {}

    open fun onPageHide() {}
}