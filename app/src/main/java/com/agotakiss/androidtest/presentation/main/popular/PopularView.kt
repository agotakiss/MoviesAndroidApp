package com.agotakiss.androidtest.presentation.main.popular

import com.agotakiss.androidtest.domain.models.Movie

interface PopularView {

    fun showMovies(newMovies: List<Movie>)
    fun updateListItem(position: Int)

}