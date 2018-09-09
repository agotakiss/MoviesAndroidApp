package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.network.MovieRemoteStore
import com.agotakiss.movie4u.data.store.FavoriteMovieDataStore
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.PagedResult
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val favoriteMovieDataStore: FavoriteMovieDataStore,
    private val movieRemoteStore: MovieRemoteStore
) : MovieRepository {

    override fun getPopularMovies(page: Int): Single<PagedResult<Movie>> =
        movieRemoteStore.getPopularMovies(page)

    override fun getSimilarMovies(movieId: Int, page: Int): Single<PagedResult<Movie>> =
        movieRemoteStore.getSimilarMovies(movieId, page)

    override fun getActorsMovies(actorId: Int): Single<List<Movie>> =
        movieRemoteStore.getActorsMovies(actorId)

    override fun getMovieDetails(movieId: Int): Single<Movie> =
        movieRemoteStore.getMovieDetails(movieId)

    override fun addToFavoriteMovies(movie: Movie): Completable =
        favoriteMovieDataStore.addToFavoriteMovies(movie)

    override fun deleteFromFavoriteMovies(movieId: Int): Completable =
        favoriteMovieDataStore.deleteFromFavoriteMovies(movieId)

    override fun getFavoriteMovies(): Single<List<Movie>> =
        favoriteMovieDataStore.getFavoriteMovies()

    override fun getSearchResults(queryString: String, page: Int): Single<PagedResult<Movie>> =
        movieRemoteStore.getSearchResults(queryString, page)
}