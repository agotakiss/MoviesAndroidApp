package com.agotakiss.movie4u.domain.repository

import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.PagedResult
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {

    fun getPopularMovies(page: Int): Single<PagedResult<Movie>>

    fun getSimilarMovies(movieId: Int, page: Int): Single<PagedResult<Movie>>

    fun getActorsMovies(actorId: Int): Single<List<Movie>>

    fun getMovieDetails(movieId: Int): Single<Movie>

    fun addToFavoriteMovies(movie: Movie): Completable

    fun deleteFromFavoriteMovies(movieId: Int): Completable

    fun getFavoriteMovies(): Single<List<Movie>>

    fun getSearchResults(queryString: String, page: Int): Single<PagedResult<Movie>>
}
