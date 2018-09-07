package com.agotakiss.movie4u.presentation.main.popular

import com.agotakiss.movie4u.domain.models.Movie

interface PopularView {

    fun showMovies(newMovies: List<Movie>)
    fun showError(t: Throwable)
    fun hideLoadingView()
    fun updateListItem(position: Int)
}