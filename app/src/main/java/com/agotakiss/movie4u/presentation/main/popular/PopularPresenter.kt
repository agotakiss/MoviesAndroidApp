package com.agotakiss.movie4u.presentation.main.popular

import com.agotakiss.movie4u.base.BasePresenter
import com.agotakiss.movie4u.data.store.ChangeInFavoriteMoviesEvent
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.GetPopularMovies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class PopularPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getPopularMovies: GetPopularMovies
) : BasePresenter() {

    private var page = 1
    private var totalPages: Int = 0
    private var movieList = mutableListOf<Movie>()
    lateinit var view: PopularView

    fun onViewReady(view: PopularView) {
        this.view = view
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        getPopularMovies.get(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onMoviesLoaded(movieList, Integer.MAX_VALUE) }, { throwable ->
                view.showError(throwable)
            })
    }

    fun onScrollEndReached() {
        page++
        if (page < totalPages) {
            loadPopularMovies()
        }
    }

    private fun onMoviesLoaded(newMovies: List<Movie>, totalPages: Int) {
        this.totalPages = totalPages
        this.movieList.addAll(newMovies)
        view.showMovies(newMovies)

        if (page == 1) {
            view.hideLoadingView()
        }
    }

    fun onFavoriteButtonClicked(position: Int) {
        val movie = movieList[position]
        movie.isFavorite = !movie.isFavorite
        if (movie.isFavorite) {
            addMovieToFavorites(movie)
        } else {
            removeMovieFromFavorites(movie)
        }
        view.updateListItem(position)
    }

    private fun removeMovieFromFavorites(movie: Movie) {
        movieRepository.deleteFromFavoriteMovies(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, { throwable ->
                logE(throwable)
            })
    }

    private fun addMovieToFavorites(movie: Movie) {
        movieRepository.addToFavoriteMovies(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, { throwable ->
                logE(throwable)
            })
    }

    @Subscribe
    fun onChangeInFavoriteMoviesEvent(changeEvent: ChangeInFavoriteMoviesEvent) {
        val movie = movieList.find { it.id == changeEvent.movieId }
        movie?.let { movie.isFavorite = changeEvent.isFavorite }
        movieList.indexOfFirst { it.id == changeEvent.movieId }
            .takeIf { it >= 0 }
            ?.let {
                view.updateListItem(it)
            }
    }
}
