package com.agotakiss.androidtest.data.repository

import android.util.Log
import com.agotakiss.androidtest.data.mapper.toMovie
import com.agotakiss.androidtest.data.models.MovieApiModel
import com.agotakiss.androidtest.data.store.FavoriteMovieDataStore
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.domain.models.Genre
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val favoriteMovieDataStore: FavoriteMovieDataStore
) : MovieRepository {

    override fun addToFavoriteMovies(movie: Movie): Completable {
        Log.d("MovieRepository", "add method called")
        return favoriteMovieDataStore.addToFavoriteMovies(movie)
    }


    override fun deleteFromFavoriteMovies(movieId: Int): Completable {
        return favoriteMovieDataStore.deleteFromFavoriteMovies(movieId)
    }

    override fun getFavoriteMovies(): Single<List<Movie>> {
        return favoriteMovieDataStore.getFavoriteMovies()
    }

//    override fun findByMovieId(movieId: Int): Single<Movie> {
//        return favoriteMovieDataStore.findFavoriteMovieById(movieId)
//    }

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

}
