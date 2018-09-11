package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.PagerFactory
import com.agotakiss.movie4u.domain.paging.PagingType
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagerFactory: PagerFactory
) {
    private val pager = pagerFactory.createPager(PagingType.POPULAR_MOVIES)

    fun get(): Single<List<Movie>> {
        return pager.getNextPage().zipWith(movieRepository.getFavoriteMovies(),
            BiFunction<List<Movie>, List<Movie>, List<Movie>> { popularMovies, favoriteMovies ->
                val moviesToDisplay = mutableListOf<Movie>()

                for (i in 0 until popularMovies.size) {
                    val movie = popularMovies[i]
                    if ((favoriteMovies.find { it.id == movie.id }) != null) {
                        movie.isFavorite = true
                    }
                    moviesToDisplay.add(movie)
                }
                moviesToDisplay
            })
    }
}