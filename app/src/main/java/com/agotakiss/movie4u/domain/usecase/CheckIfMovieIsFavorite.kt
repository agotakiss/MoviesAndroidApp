package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import com.agotakiss.movie4u.utils.mapListItems
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