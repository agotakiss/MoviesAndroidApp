package com.agotakiss.androidtest.presentation.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.injector.Injector
import com.agotakiss.androidtest.presentation.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    private val movieRepository = Injector.getMovieRepository()
    private var page = 1
    private var totalPages: Int = 0
    internal var movieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeList()
        loadNextPopularMoviesPage()
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        recycler_view.layoutManager = layoutManager
        adapter = MovieAdapter(movieList, this@MainActivity)
        recycler_view.adapter = adapter
        adapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                page++
                if (page < totalPages) {
                    loadNextPopularMoviesPage()
                }
            }
        })
    }

    private fun loadNextPopularMoviesPage() {
        movieRepository.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieList -> onMoviesLoaded(movieList, Integer.MAX_VALUE) }, { throwable ->
                    logE(throwable)
                    Toast.makeText(this@MainActivity, "Error: " + throwable.toString(), Toast.LENGTH_SHORT).show()
                })
    }

    private fun onMoviesLoaded(newMovies: List<Movie>, pages: Int) {
        movieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
        totalPages = pages
    }
}
