package com.agotakiss.movie4u.presentation.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.agotakiss.movie4u.presentation.main.favorites.FavoritesFragment
import com.agotakiss.movie4u.presentation.main.popular.PopularFragment
import com.agotakiss.movie4u.presentation.main.search.SearchFragment
import java.lang.ref.WeakReference

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private var numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager) {

    private val pages = mutableMapOf<Int, WeakReference<MainPageFragment>>()

    override fun getItem(position: Int): MainPageFragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> FavoritesFragment()
            2 -> SearchFragment()
            else -> error("invalid ")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as MainPageFragment
        pages[position] = WeakReference(fragment)
        return fragment
    }

    fun getItemByPosition(position: Int) = pages[position]?.get()

    override fun getCount(): Int {
        return numberOfTabs
    }
}