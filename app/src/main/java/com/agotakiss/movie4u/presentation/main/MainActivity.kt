package com.agotakiss.movie4u.presentation.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val mainPagerAdapter = ViewPagerAdapter(supportFragmentManager, 3)
        viewpager.adapter = mainPagerAdapter
        viewpager.offscreenPageLimit = 3

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.popular -> viewpager.currentItem = 0
                R.id.favorites -> viewpager.currentItem = 1
                R.id.search -> viewpager.currentItem = 2
            }
            true
        }

        viewpager.offscreenPageLimit = 3
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var lastPage: MainPageFragment? = null
            override fun onPageSelected(position: Int) {
                lastPage?.onPageHide()
                lastPage = mainPagerAdapter.getItemByPosition(position)?.apply {
                    onPageShow()
                }
                when (position) {
                    0 -> {
                        viewpager.shouldSkipHorizontalSwipe = false
                        bottom_navigation.selectedItemId = R.id.popular
                    }
                    1 -> {
                        viewpager.shouldSkipHorizontalSwipe = false
                        bottom_navigation.selectedItemId = R.id.favorites
                    }
                    2 -> {
                        viewpager.shouldSkipHorizontalSwipe = false
                        bottom_navigation.selectedItemId = R.id.search
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })

        viewpager.currentItem = 0
        viewpager.shouldSkipHorizontalSwipe = false
    }

    fun hideLoadingView() {
        loading_view_container.visibility = View.GONE
        bottom_navigation.visibility = View.VISIBLE
    }
}