package com.agotakiss.androidtest.presentation.main

import com.agotakiss.androidtest.domain.models.Movie

interface MainView {

    fun showMovies(newMovies: List<Movie>)

}