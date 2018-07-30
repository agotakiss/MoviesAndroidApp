package com.agotakiss.androidtest.presentation.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       var numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            1 -> FavoritesFragment()
            2 -> SearchFragment()
            else -> error("invalid ")
        }
    }

    override fun getCount(): Int {
        return numberOfTabs
    }
}