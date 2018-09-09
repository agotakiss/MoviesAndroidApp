package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.paging.PagerFactory
import com.agotakiss.movie4u.domain.paging.PagingType
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSearchResults @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagerFactory: PagerFactory
) {

    var pager: Pager<Movie>? = null

    fun get(queryString: String): Single<List<Movie>> {
        if (pager == null || pager!!.param != queryString) {
            pager = pagerFactory.createPager(PagingType.SEARCH_MOVIES, queryString)
        }
        return pager!!.getNextPage().zipWith(movieRepository.getFavoriteMovies(),
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