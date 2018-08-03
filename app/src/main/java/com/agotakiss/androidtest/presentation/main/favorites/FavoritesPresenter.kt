package com.agotakiss.androidtest.presentation.main.favorites

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.data.models.MovieDatabaseModel
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.presentation.main.MainActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val movieRepository: MovieRepository
) : BasePresenter() {

    private var favoriteMoviesList = emptyList<Movie>()

    lateinit var view: FavoritesView

    fun onViewReady(view: FavoritesView) {
        this.view = view
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        movieRepository.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onMoviesLoaded(movieList) }, { throwable ->
                logE(throwable)
            })
        logE("getfavoritemovies called")
    }

    private fun onMoviesLoaded(newMovies: List<Movie>) {
        this.favoriteMoviesList = newMovies
        view.showFavoriteMovies(newMovies)
    }

    fun onFavoriteItemClicked(movie: Movie){

    }

    fun onFavoriteButtonClicked(movie: Movie) {
       movieRepository.deleteFromFavoriteMovies(movie.id)

    }

}