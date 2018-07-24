package com.agotakiss.androidtest.presentation.detail

import android.util.Log
import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val movieRepository: MovieRepository
) : BasePresenter() {

    private var similarMoviesPage = 1
    private var totalPages: Int = 0
    lateinit var view: DetailsView
    lateinit var movie: Movie

    fun onViewReady(view: DetailsView, movie: Movie) {
        this.view = view
        this.movie = movie
        loadSimilarMovies()
    }

    private fun loadSimilarMovies() {
        movieRepository.getSimilarMovies(movie.id, similarMoviesPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onSimilarMoviesLoaded(movieList, Integer.MAX_VALUE) }, { throwable ->
                view.showError(throwable)
            })
    }

    private fun onSimilarMoviesLoaded(newMovies: List<Movie>, totalPages: Int) {
        this.totalPages = totalPages
        view.showSimilarMovies(newMovies)
    }

    fun onScrollEndReached() {
        Log.d("DetailPresenter", "onScrollEndReached")
        similarMoviesPage++
        if (similarMoviesPage < totalPages) {
            loadSimilarMovies()
        }
    }
}