package com.agotakiss.androidtest.presentation.main.popular

import android.content.Context
import android.util.Log
import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.data.store.FavoriteMovieDeleteEvent
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.Subscribe
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
        logE("mainpresenter add called")
//        Toast.makeText(this, "You added " + movie.title + " to your favorites!", Toast.LENGTH_LONG).show()
    }


    @Subscribe
    fun onFavoriteMovieFromFavoriteFragmentDeleted(movieDeleteEvent: FavoriteMovieDeleteEvent) {
        movieDeleteEvent.movieId
        logD("$movieDeleteEvent deleted with eventbus")
        val movie = movieList.find { it.id == movieDeleteEvent.movieId }
        movie!!.isFavorite = !movie.isFavorite
        movieList.indexOfFirst { it.id == movieDeleteEvent.movieId }
            .takeIf { it >= 0 }
            ?.let {view.updateListItem(it)

            }
    }

}
