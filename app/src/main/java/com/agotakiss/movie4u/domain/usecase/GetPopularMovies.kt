package com.agotakiss.movie4u.domain.usecase

import com.agotakiss.movie4u.domain.helper.FavoriteMovieMerger
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.Pager
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Named

class GetPopularMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteMovieMerger: FavoriteMovieMerger,
    @Named("popular") private val pager: Pager<Movie>
) {

    fun get(): Single<List<Movie>> =
        pager.getNextPage().zipWith(movieRepository.getFavoriteMovies(),
            BiFunction<List<Movie>, List<Movie>, List<Movie>> { popularMovies, favoriteMovies ->
                favoriteMovieMerger.merge(favoriteMovies, popularMovies)
            })
}