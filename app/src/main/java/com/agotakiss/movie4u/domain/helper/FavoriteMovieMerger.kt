package com.agotakiss.movie4u.domain.helper

import com.agotakiss.movie4u.domain.models.Movie
import javax.inject.Inject

class FavoriteMovieMerger @Inject constructor() {

    fun merge(favorites: List<Movie>, movieList: List<Movie>): List<Movie> =
        movieList.map { movie ->
            if (favorites.find { it.id == movie.id } != null) {
                movie.isFavorite = true
            }
            movie
        }
}