package com.agotakiss.androidtest.presentation.detail

import android.util.Log
import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.CastRepository
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    private val castRepository: CastRepository
) : BasePresenter() {

    private var similarMoviesPage = 1
    private var totalPages: Int = 0
    private var actorsTotalPage = 0
    lateinit var view: DetailsView
    lateinit var movie: Movie

    fun onViewReady(view: DetailsView, movie: Movie) {
        this.view = view
        this.movie = movie
        loadSimilarMovies()
        loadActors()
    }

    fun onFavoriteButtonClicked(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        if (movie.isFavorite) {
            addMovieToFavorites(movie)
            view.setFavoriteButton(true)
        } else {
            removeMovieFromFavorites(movie)
            view.setFavoriteButton(false)
        }
    }

    private fun removeMovieFromFavorites(movie: Movie) {
        movieRepository.deleteFromFavoriteMovies(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                logD("movie delete completed")
            }, { throwable ->
                logE(throwable)
            })
    }

    private fun addMovieToFavorites(movie: Movie) {
        movieRepository.addToFavoriteMovies(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                logD("addMovieToFavorites completed")
            }, { throwable ->
                logE(throwable)
            })
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

    private fun loadActors() {
        castRepository.getCast(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ actorsList ->
                onActorsLoaded(actorsList, Int.MAX_VALUE)
            }, { t ->
                view.showError(t)
            })
    }

    private fun onActorsLoaded(newActors: List<Cast>, actorsTotalPage: Int) {
        this.actorsTotalPage = actorsTotalPage
        view.showActors(newActors)
    }

//    fun onActorsScrollEndReached() {
//        Log.d("DetailPresenter", "onActorsScrollEndReached")
//        actorsPage++
//        if (actorsPage < actorsTotalPage) {
//            loadActors()
//        }
//    }

    fun onSimilarMovieScrollEndReached() {
        Log.d("DetailPresenter", "onSimilarMovieScrollEndReached")
        similarMoviesPage++
        if (similarMoviesPage < totalPages) {
            loadSimilarMovies()
        }
    }
}