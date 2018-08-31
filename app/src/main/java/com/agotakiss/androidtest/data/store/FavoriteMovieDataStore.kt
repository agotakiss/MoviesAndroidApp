package com.agotakiss.androidtest.data.store

import android.util.Log
import com.agotakiss.androidtest.data.mapper.toMovie
import com.agotakiss.androidtest.data.models.MovieDatabaseModel
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.domain.repository.GenreRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class FavoriteMovieDataStore @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val genreRepository: GenreRepository
) {
    fun addToFavoriteMovies(movie: Movie): Completable {
        Log.d("databasestore", "add method called")
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
            Log.d("insert", movieDatabaseModel.title + " inserted")
        }

    }

    fun deleteFromFavoriteMovies(movieId: Int): Completable {
        val event = FavoriteMovieDeleteEvent()
        event.movieId = movieId
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

//    fun findFavoriteMovieById(movieId : Int):Single<Movie>{
//return Single.fromCallable { favoriteMovieDao.findByMovieId(movieId) }
//    .
//    }
}