package com.agotakiss.movie4u.presentation.detail

import com.agotakiss.movie4u.base.BasePresenter
import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.repository.CastRepository
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.CheckIfMovieIsFavorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class DetailsPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    @Named("similar") private val similarMoviePager: Pager<Movie>,
    private val checkIfMovieIsFavorite: CheckIfMovieIsFavorite,
    private val castRepository: CastRepository
) : BasePresenter() {

    lateinit var view: DetailsView
    lateinit var movie: Movie

    fun onViewReady(view: DetailsView, movie: Movie) {
        this.view = view
        this.movie = movie
        checkIfMovieIsFavorite(movie)
        loadSimilarMovies()
        loadActors()
    }

    fun onFavoriteButtonClicked(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        view.hasChangedFavoriteState()
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

    private fun checkIfMovieIsFavorite(movie: Movie) {
        checkIfMovieIsFavorite.check(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isFavorite -> view.setFavoriteButton(isFavorite) }, { t ->
                Timber.e(t)
            })
    }

    private fun loadSimilarMovies() {
        similarMoviePager.getNextPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onSimilarMoviesLoaded(movieList) }, { t ->
                Timber.e(t)
            })
    }

    private fun onSimilarMoviesLoaded(newMovies: List<Movie>) {
        view.showSimilarMovies(newMovies)
    }

    private fun loadActors() {
        castRepository.getCast(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ actorsList ->
                onActorsLoaded(actorsList)
            }, { t ->
                Timber.e(t)
            })
    }

    private fun onActorsLoaded(newActors: List<Cast>) {
        view.showActors(newActors)
    }

    fun onSimilarMovieScrollEndReached() {
        loadSimilarMovies()
    }
}