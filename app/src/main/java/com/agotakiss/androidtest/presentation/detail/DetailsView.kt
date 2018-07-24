package com.agotakiss.androidtest.presentation.detail

import com.agotakiss.androidtest.domain.models.Movie


interface DetailsView {

    fun showSimilarMovies(newMovies: List<Movie>)

    fun showError(t : Throwable)

}