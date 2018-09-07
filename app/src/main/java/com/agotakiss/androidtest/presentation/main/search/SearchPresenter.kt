package com.agotakiss.androidtest.presentation.main.search

import com.agotakiss.androidtest.base.BasePresenter
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.domain.usecase.GetSearchResults
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getSearchResults: GetSearchResults
) : BasePresenter() {

    lateinit var view: SearchView
    private var searchResultList = mutableListOf<Movie>()
    private var page = 1
    private var lastQueryString = ""
    private val queryTextChangeSubject = PublishSubject.create<String>()

    fun onViewReady(view: SearchView) {
        this.view = view
        initSearchQueryObserver()
    }

    private fun initSearchQueryObserver() {
        queryTextChangeSubject
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .subscribe({
                loadSearchResults(it)
            }, {
                logE(it)
            })
    }

    fun onSearchQueryChanged(queryString: String) {
        queryTextChangeSubject.onNext(queryString)
    }

    fun onDoneButtonClicked(queryString: String) {
        loadSearchResults(queryString)
    }

    private fun loadSearchResults(queryString: String) {
        if (queryString == "") return
        getSearchResults.get(queryString, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchResultList -> onSearchResultsLoaded(searchResultList, queryString) },
                { throwable ->
                    view.showError(throwable)
                })
    }

    private fun onSearchResultsLoaded(newSearchResults: List<Movie>, queryString: String) {
        if (page == 1 && newSearchResults.isEmpty()) {
            view.showNoResult()
        } else if (queryString != lastQueryString && newSearchResults.isNotEmpty()) {
            lastQueryString = queryString
            this.searchResultList.clear()
            this.searchResultList.addAll(newSearchResults)
            view.showSearchResults(searchResultList)
        }
        if (page != 1 && queryString == lastQueryString) {
            lastQueryString = queryString
            this.searchResultList.addAll(newSearchResults)
            view.showNextPage(newSearchResults)
        }
    }

    fun onScrollEndReached() {
        page++
        loadSearchResults(lastQueryString)
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