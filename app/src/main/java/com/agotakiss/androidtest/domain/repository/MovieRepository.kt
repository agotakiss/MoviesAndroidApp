package com.agotakiss.androidtest.domain.repository

import com.agotakiss.androidtest.domain.models.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {

    fun getPopularMovies(page: Int): Single<List<Movie>>

    fun getSimilarMovies(movieId: Int, page: Int): Single<List<Movie>>

    fun getActorsMovies(actorId: Int): Single<List<Movie>>

    fun getMovieDetails(movieId: Int): Single<Movie>

    fun addToFavoriteMovies(movie: Movie): Completable

    fun deleteFromFavoriteMovies(movieId: Int): Completable

    fun getFavoriteMovies(): Single<List<Movie>>

    fun getSearchResults(queryString: String, page: Int): Single<List<Movie>>
}
