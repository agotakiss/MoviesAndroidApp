package com.agotakiss.androidtest.presentation.main.search

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.domain.usecase.GetSearchResults
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter @Inject constructor
(private val movieRepository: MovieRepository,
 private val getSearchResults: GetSearchResults
) : BasePresenter() {

    lateinit var view: SearchView
    private var searchResultList = mutableListOf<Movie>()
    private var page = 1;
    private var queryString = ""

    fun onViewReady(view: SearchView) {
        this.view = view
    }

    fun onSearchButtonClicked(queryString: String) {
        if (queryString != "") {
            if (this.queryString == "") {
                this.queryString = queryString
                loadSearchResults()
            } else if (queryString != this.queryString) {
                this.queryString = queryString
                page = 1
                loadSearchResults()
            }
        }
    }

    fun loadSearchResults() {
        getSearchResults.get(queryString, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchResultList -> onSearchResultsLoaded(searchResultList) },
                { throwable ->
                    view.showError(throwable)
                })
    }

    private fun onSearchResultsLoaded(newSearchResults: List<Movie>) {
        if (newSearchResults.isEmpty()) {
            view.showNoResult()
        }
        if (page == 1) {
            this.searchResultList.clear()
            this.searchResultList.addAll(newSearchResults)
            view.showSearchResults(searchResultList)
        } else {
            this.searchResultList.addAll(newSearchResults)
            view.showNextPage(newSearchResults)
        }
    }

    fun onScrollEndReached() {
        page++
        loadSearchResults()
    }

    fun onFavoriteButtonClicked(position: Int) {
        val movie = searchResultList[position]
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
}