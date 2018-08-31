package com.agotakiss.androidtest.presentation.main.search

import com.agotakiss.androidtest.domain.models.Movie

interface SearchView {
    fun showSearchResults(newSearchResults: List<Movie>)
    fun showNextPage(newSearchResults: List<Movie>)
    fun showNoResult()
    fun showError(throwable: Throwable)
    fun updateListItem(position: Int)
}