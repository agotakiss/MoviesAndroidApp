package com.agotakiss.movie4u.presentation.detail

import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.domain.models.Movie

interface DetailsView {

    fun showSimilarMovies(newMovies: List<Movie>)

    fun showActors(newActors: List<Cast>)

    fun showError(t: Throwable)
    fun setFavoriteButton(isFavorite: Boolean)
    fun hasChangedFavoriteState()
}