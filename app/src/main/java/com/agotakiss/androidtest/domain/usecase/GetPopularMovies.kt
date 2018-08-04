package com.agotakiss.androidtest.domain.usecase

import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun get(page: Int): Single<List<Movie>> {
        return movieRepository.getPopularMovies(page).zipWith(movieRepository.getFavoriteMovies(),
            BiFunction<List<Movie>, List<Movie>, List<Movie>> { popularMovies, favoriteMovies ->
                val moviesToDisplay = mutableListOf<Movie>()

                for (i in 0 until popularMovies.size) {
                    val movie = popularMovies[i]
                    if (favoriteMovies.contains(movie)) {
                        movie.isFavorite = true
                    }
                    moviesToDisplay.add(movie)
                }
                moviesToDisplay
            })
    }
}