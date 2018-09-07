package com.agotakiss.movie4u.presentation.main.favorites

import com.agotakiss.movie4u.domain.models.Movie

interface FavoritesView {

    fun showFavoriteMovies(favoriteMovies: List<Movie>)

    fun updateFavoriteMovies(position: Int)

    fun showNoFavoritesView()
}