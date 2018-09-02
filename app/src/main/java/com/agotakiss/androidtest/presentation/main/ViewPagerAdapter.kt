package com.agotakiss.androidtest.presentation.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.agotakiss.androidtest.presentation.main.favorites.FavoritesFragment
import com.agotakiss.androidtest.presentation.main.popular.PopularFragment
import com.agotakiss.androidtest.presentation.main.search.SearchFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       var numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> FavoritesFragment()
            2 -> SearchFragment()
            else -> error("invalid ")
        }
    }

    override fun getCount(): Int {
        return numberOfTabs
    }
}