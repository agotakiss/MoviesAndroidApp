package com.agotakiss.androidtest.presentation.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    val applicationComponent by lazy { movieApplication.applicationComponent.plus(MainModule(this)) }

    @Inject
    lateinit var presenter: MainPresenter

    internal var movieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applicationComponent.inject(this)
        initializeList()
        presenter.onViewReady(this)
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(this)
        main_recycler_view.layoutManager = layoutManager
        adapter = MovieAdapter(movieList, this)
        main_recycler_view.adapter = adapter
        adapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                    presenter.onScrollEndReached()
            }
        })
    }
    override fun showMovies(newMovies: List<Movie>) {
        movieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
    }
}
