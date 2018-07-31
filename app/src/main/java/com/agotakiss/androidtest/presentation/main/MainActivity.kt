package com.agotakiss.androidtest.presentation.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    //    , MainView
//
//    companion object {
//        const val POSTER_TRANSITION_NAME = "posterTransition"
//    }
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
        init()
    }

    fun init(){
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

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        viewpager.shouldSkipHorizontalSwipe = false
                        bottom_navigation.selectedItemId = R.id.popular
                    }
                    1 -> {
                        viewpager.shouldSkipHorizontalSwipe = true
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

        viewpager.currentItem = 1
        viewpager.shouldSkipHorizontalSwipe = true
    }


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

