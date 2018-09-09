package com.agotakiss.movie4u.data.network

import com.agotakiss.movie4u.data.mapper.toMovie
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.paging.PagedResult
import com.agotakiss.movie4u.domain.repository.GenreRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieRemoteStore @Inject constructor(
    private val movieDbApi: MovieDbApi,
    private val genreRepository: GenreRepository
) {

    fun getPopularMovies(page: Int): Single<PagedResult<Movie>> =
        genreRepository.getGenres().flatMap { genreMap ->
            movieDbApi.getPopularMovies(page)
                .map { PagedResult(it.movies?.map { it.toMovie(genreMap) } ?: emptyList(), it.totalPages) }
        }

    fun getMovieDetails(movieId: Int): Single<Movie> =
        genreRepository.getGenres().flatMap { genreMap ->
            movieDbApi.getMovieDetails(movieId)
                .map { it.toMovie(genreMap) }
        }

    fun getSimilarMovies(movieId: Int, page: Int): Single<PagedResult<Movie>> =
        genreRepository.getGenres().flatMap { genreMap ->
            movieDbApi.getSimilarMovies(movieId, page)
                .map { PagedResult(it.movies?.map { it.toMovie(genreMap) } ?: emptyList(), it.totalPages) }
        }

    fun getActorsMovies(actorId: Int): Single<List<Movie>> =

        genreRepository.getGenres().flatMap { genreMap ->
            movieDbApi.getActorsMovies(actorId)
                .map { it.cast?.map { it.toMovie(genreMap) } ?: emptyList() }
        }

    fun getSearchResults(queryString: String, page: Int): Single<PagedResult<Movie>> =
        genreRepository.getGenres().flatMap { genreMap ->
            movieDbApi.getSearchResults(queryString, page)
                .map { PagedResult(it.movies?.map { it.toMovie(genreMap) } ?: emptyList(), it.totalPages) }
        }
}