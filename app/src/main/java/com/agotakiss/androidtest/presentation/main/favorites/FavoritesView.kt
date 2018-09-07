package com.agotakiss.androidtest.presentation.main.favorites

import com.agotakiss.androidtest.domain.models.Movie

interface FavoritesView {

    fun showFavoriteMovies(favoriteMovies: List<Movie>)

    fun updateFavoriteMovies(position: Int)

    fun showNoFavoritesView()
}