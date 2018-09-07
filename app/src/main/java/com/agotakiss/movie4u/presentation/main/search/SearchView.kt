package com.agotakiss.movie4u.presentation.main.search

import com.agotakiss.movie4u.domain.models.Movie

interface SearchView {
    fun showSearchResults(newSearchResults: List<Movie>)
    fun showNextPage(newSearchResults: List<Movie>)
    fun showNoResult()
    fun showError(throwable: Throwable)
    fun updateListItem(position: Int)
}