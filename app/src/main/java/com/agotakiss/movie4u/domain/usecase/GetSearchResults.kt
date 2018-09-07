package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSearchResults @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun get(queryString: String, page: Int): Single<List<Movie>> {
        return movieRepository.getSearchResults(queryString, page).zipWith(movieRepository.getFavoriteMovies(),
            BiFunction<List<Movie>, List<Movie>, List<Movie>> { searchResults, favoriteMovies ->
                val moviesToDisplay = mutableListOf<Movie>()

                for (i in 0 until searchResults.size) {
                    val movie = searchResults[i]
                    if (favoriteMovies.contains(movie)) {
                        movie.isFavorite = true
                    }
                    moviesToDisplay.add(movie)
                }
                moviesToDisplay
            })
    }
}