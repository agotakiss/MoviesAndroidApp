package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toMovie
import com.agotakiss.androidtest.data.models.MovieApiModel
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.domain.models.Genre
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single

class MovieRepositoryImpl : MovieRepository {

    private val movieDbApi = Injector.getMovieDbApi()
    private val genreRepository = Injector.getGenreRepository()
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
            .map{it.cast}
            .toObservable()
            .flatMapIterable<MovieApiModel> { it }
            .map { it.toMovie(genresMap) }
            .toList()
    }

}
