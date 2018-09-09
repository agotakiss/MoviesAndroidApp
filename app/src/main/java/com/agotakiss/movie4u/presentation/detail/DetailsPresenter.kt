package com.agotakiss.movie4u.presentation.detail

import com.agotakiss.movie4u.base.BasePresenter
import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.paging.PagerFactory
import com.agotakiss.movie4u.domain.paging.PagingType
import com.agotakiss.movie4u.domain.repository.CastRepository
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.domain.usecase.CheckIfMovieIsFavorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagerFactory: PagerFactory,
    private val checkIfMovieIsFavorite: CheckIfMovieIsFavorite,
    private val castRepository: CastRepository
) : BasePresenter() {

    lateinit var view: DetailsView
    lateinit var movie: Movie
    lateinit var similarMoviePager: Pager<Movie>

    fun onViewReady(view: DetailsView, movie: Movie) {
        this.view = view
        this.movie = movie
        this.similarMoviePager = pagerFactory.createPager(PagingType.SIMILAR_MOVIES, movie.id)
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
                view.showError(t)
            })
    }

    private fun loadSimilarMovies() {
        similarMoviePager.getNextPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList -> onSimilarMoviesLoaded(movieList) }, { throwable ->
                view.showError(throwable)
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
                view.showError(t)
            })
    }

    private fun onActorsLoaded(newActors: List<Cast>) {
        view.showActors(newActors)
    }

    fun onSimilarMovieScrollEndReached() {
        loadSimilarMovies()
    }
}