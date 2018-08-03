package com.agotakiss.androidtest.presentation.main.popular

import com.agotakiss.androidtest.domain.models.Movie

interface MainView {

    fun showMovies(newMovies: List<Movie>)
    fun updateListItem(position: Int)

}