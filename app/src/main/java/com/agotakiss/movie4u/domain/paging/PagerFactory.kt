package com.agotakiss.movie4u.domain.paging

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.MovieRepository
import javax.inject.Inject

class PagerFactory @Inject constructor(
    private val movieRepository: MovieRepository
) {

    fun createPager(type: PagingType, param: Any? = null): Pager<Movie> {
        return when (type) {
            PagingType.POPULAR_MOVIES -> Pager(
                { page, param -> movieRepository.getPopularMovies(page) },
                param
            )
            PagingType.SEARCH_MOVIES -> Pager({ page, param ->
                movieRepository.getSearchResults(
                    param!! as String,
                    page
                )
            }, param)
            PagingType.SIMILAR_MOVIES -> Pager(
                { page, param -> movieRepository.getSimilarMovies(param!! as Int, page) },
                param
            )
        }
    }
}