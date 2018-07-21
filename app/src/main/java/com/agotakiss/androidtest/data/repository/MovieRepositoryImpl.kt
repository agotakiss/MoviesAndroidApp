package com.agotakiss.androidtest.data.repository

import com.agotakiss.androidtest.data.mapper.toMovie
import com.agotakiss.androidtest.data.models.MovieApiModel
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import com.agotakiss.androidtest.injector.Injector
import io.reactivex.Single

class MovieRepositoryImpl : MovieRepository {

    private val movieDbApi = Injector.getMovieDbApi()
    private val genreRepository = Injector.getGenreRepository()

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return movieDbApi.getPopularMovies(page)
                .map<List<MovieApiModel>> { it.movies }
                .toObservable()
                .flatMapIterable<MovieApiModel> { it }
                .map { it.toMovie(genreRepository.getGenres().blockingGet()) }
                .toList()
    }

    override fun getSimilarMovies(movieId: Int, page: Int): Single<List<Movie>> {
        return movieDbApi.getSimilarMovies(movieId, page)
                .map<List<MovieApiModel>> { it.movies }
                .toObservable()
                .flatMapIterable<MovieApiModel> { it }
                .map { it.toMovie(genreRepository.getGenres().blockingGet()) }
                .toList()
    }
}
