package com.agotakiss.movie4u.data.store

import com.agotakiss.movie4u.data.mapper.toMovie
import com.agotakiss.movie4u.data.models.MovieDatabaseModel
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.domain.repository.GenreRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class FavoriteMovieDataStore @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val genreRepository: GenreRepository
) {
    fun addToFavoriteMovies(movie: Movie): Completable {
        val event = ChangeInFavoriteMoviesEvent()
        event.movieId = movie.id
        event.isFavorite = true
        EventBus.getDefault().post(event)

        return Completable.fromCallable {
            val genreIdList = mutableListOf<Int>()
            for (i in 0 until movie.genres!!.size) {
                genreIdList.add(movie.genres!![i].id)
            }
            val movieDatabaseModel = MovieDatabaseModel(movie.posterPath, movie.isAdult,
                movie.overview, movie.releaseDateText, genreIdList, movie.id,
                movie.originalTitle, movie.originalLanguage, movie.title,
                movie.backdropPath, movie.popularity, movie.voteCount,
                movie.isVideo, movie.averageVote, movie.isFavorite, System.currentTimeMillis())

            favoriteMovieDao.insert(movieDatabaseModel)
        }
    }

    fun deleteFromFavoriteMovies(movieId: Int): Completable {
        val event = ChangeInFavoriteMoviesEvent()
        event.movieId = movieId
        event.isFavorite = false
        EventBus.getDefault().post(event)
        return Completable.fromCallable {
            favoriteMovieDao.deleteById(movieId)
        }
    }

    fun getFavoriteMovies(): Single<List<Movie>> {
        return Single.fromCallable { genreRepository.getGenres().blockingGet() }.flatMap { genres ->
            favoriteMovieDao.getAll()
                .toObservable()
                .flatMapIterable { it }
                .map { it.toMovie(genres) }
                .toList()
        }
    }
}