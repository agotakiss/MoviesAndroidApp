package com.agotakiss.androidtest.domain.usecase

import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.utils.mapListItems
import io.reactivex.Single
import javax.inject.Inject

class CheckIfMovieIsFavorite @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun check(movie: Movie): Single<Boolean> =
        movieRepository.getFavoriteMovies()
            .mapListItems { it.id }
            .map { it.contains(movie.id) }
}