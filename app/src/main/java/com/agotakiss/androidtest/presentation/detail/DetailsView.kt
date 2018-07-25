package com.agotakiss.androidtest.presentation.detail

import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.models.Movie


interface DetailsView {

    fun showSimilarMovies(newMovies: List<Movie>)

    fun showActors(newActors: List<Cast>)

    fun showError(t : Throwable)

}