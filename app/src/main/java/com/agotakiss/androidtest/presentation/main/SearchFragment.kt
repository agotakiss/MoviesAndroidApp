package com.agotakiss.androidtest.presentation.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.agotakiss.androidtest.R

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this mainFragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


}
