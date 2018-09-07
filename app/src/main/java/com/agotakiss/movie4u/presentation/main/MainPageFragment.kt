package com.agotakiss.movie4u.presentation.main

import android.support.v4.app.Fragment

abstract class MainPageFragment : Fragment() {

    open fun onPageShow() {}

    open fun onPageHide() {}
}