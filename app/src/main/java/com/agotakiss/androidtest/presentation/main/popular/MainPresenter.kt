package com.agotakiss.androidtest.presentation.main.popular

import android.util.Log
import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val movieRepository: MovieRepository
) : BasePresenter() {
    var page = 1;
    private var totalPages: Int = 0

    private var movieList = mutableListOf<Movie>()

    lateinit var view: MainView

    fun onViewReady(view: MainView) {
        this.view = view
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        movieRepository.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onMoviesLoaded(movieList, Integer.MAX_VALUE) }, { throwable ->
                logE(throwable)
            })
    }

    fun onScrollEndReached() {
        Log.d("MainPresenter", "onSimilarMovieScrollEndReached")
        page++
        if (page < totalPages) {
            loadPopularMovies()
        }
    }

    private fun onMoviesLoaded(newMovies: List<Movie>, totalPages: Int) {
        this.totalPages = totalPages
        this.movieList.addAll(newMovies)
        view.showMovies(newMovies)
    }

    fun onFavoriteButtonClicked( position: Int) {
        val movie = movieList[position]
        movie.isFavorite = !movie.isFavorite
        view.updateFavoriteButton(position)
    }



}