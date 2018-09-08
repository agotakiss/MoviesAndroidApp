package com.agotakiss.movie4u.data.repository

import com.agotakiss.movie4u.data.mapper.toMovie
import com.agotakiss.movie4u.data.models.MovieApiModel
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.data.store.FavoriteMovieDataStore
import com.agotakiss.movie4u.domain.models.Genre
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.GenreRepository
import com.agotakiss.movie4u.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val favoriteMovieDataStore: FavoriteMovieDataStore,
    private val genreRepository: GenreRepository,
    private val movieDbApi: MovieDbApi
) : MovieRepository {

    override fun addToFavoriteMovies(movie: Movie): Completable {
        return favoriteMovieDataStore.addToFavoriteMovies(movie)
    }

    override fun deleteFromFavoriteMovies(movieId: Int): Completable {
        return favoriteMovieDataStore.deleteFromFavoriteMovies(movieId)
    }

    override fun getFavoriteMovies(): Single<List<Movie>> {
        return favoriteMovieDataStore.getFavoriteMovies()
    }

    private lateinit var genresMap: Map<Int, Genre>

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return Completable.fromAction {
            genresMap = genreRepository.getGenres().blockingGet()
        }.andThen(movieDbApi.getPopularMovies(page))
            .map { it.movies }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genresMap) }
            .toList()
    }

    override fun getMovieDetails(movieId: Int): Single<Movie> {
        return Completable.fromAction {
            genresMap = genreRepository.getGenres().blockingGet()
        }.andThen(movieDbApi.getMovieDetails(movieId))
            .map { it.toMovie(genresMap) }
    }

    override fun getSimilarMovies(movieId: Int, page: Int): Single<List<Movie>> {
        return Completable.fromAction {
            genresMap = genreRepository.getGenres().blockingGet()
        }.andThen(movieDbApi.getSimilarMovies(movieId, page))
            .map { it.movies }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genresMap) }
            .toList()
    }

    override fun getActorsMovies(actorId: Int): Single<List<Movie>> {
        return Completable.fromAction {
            genresMap = genreRepository.getGenres().blockingGet()
        }.andThen(movieDbApi.getActorsMovies(actorId))
            .map { it.cast }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genresMap) }
            .toList()
    }

    override fun getSearchResults(queryString: String, page: Int): Single<List<Movie>> {
        return Completable.fromAction {
            genresMap = genreRepository.getGenres().blockingGet()
        }.andThen(movieDbApi.getSearchResults(queryString, page))
            .map { it.movies }
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genresMap) }
            .toList()
    }
}