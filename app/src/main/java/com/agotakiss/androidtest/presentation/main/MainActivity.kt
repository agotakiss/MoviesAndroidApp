package com.agotakiss.androidtest.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.BaseActivity
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

//    , MainView
//
    companion object {
        const val POSTER_TRANSITION_NAME = "posterTransition"
    }
//
//    val applicationComponent by lazy { movieApplication.applicationComponent.plus(MainModule(this)) }
//
//    @Inject
//    lateinit var presenter: MainPresenter
//
//    internal var movieList: MutableList<Movie> = ArrayList()
//    private lateinit var adapter: MovieAdapter









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        tab_layout.addTab(tab_layout.newTab().setText("Tab 1 Item"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 2 Item"))

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,
            tab_layout.tabCount)
        pager.adapter = viewPagerAdapter

        pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })

//
//        applicationComponent.inject(this)
//        initializeList()
//        presenter.onViewReady(this)
    }





//
//
//    private fun initializeList() {
//        val layoutManager = LinearLayoutManager(this)
//        main_recycler_view.layoutManager = layoutManager
//        adapter = MovieAdapter(movieList, object : OnEndReachedListener {
//            override fun onEndReached(position: Int) {
//                presenter.onScrollEndReached()
//            }
//        }) { movie, view ->
//            val intent = Intent(this, DetailsActivity::class.java)
//            intent.putExtra(MOVIE, movie)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
//                POSTER_TRANSITION_NAME)
//            startActivity(intent, options.toBundle())
//        }
//        main_recycler_view.adapter = adapter
//    }
//
//    override fun showMovies(newMovies: List<Movie>) {
//        movieList.addAll(newMovies)
//        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
//    }
}
