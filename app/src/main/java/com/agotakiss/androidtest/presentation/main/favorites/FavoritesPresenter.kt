package com.agotakiss.androidtest.presentation.main.favorites

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
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
    }

    private fun onMoviesLoaded(newMovies: List<Movie>) {
        this.favoriteMoviesList = newMovies
        for (i in 0 until favoriteMoviesList.size){
            favoriteMoviesList[i].isFavorite = true
        }
        view.showFavoriteMovies(newMovies)
        EventBus.getDefault().post(newMovies)
    }

    fun onFavoriteButtonClicked(position : Int, movieId: Int) {
        movieRepository.deleteFromFavoriteMovies(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, { throwable ->
                logE(throwable)
            })
        view.updateFavoriteMovies(position)
    }
}