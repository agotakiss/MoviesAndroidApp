package com.agotakiss.androidtest.data.store

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.agotakiss.androidtest.data.models.MovieDatabaseModel
import io.reactivex.Single

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): Single<List<MovieDatabaseModel>>

    @Query("SELECT * FROM favorite_movies where id LIKE  :movieId")
    fun findByMovieId(movieId: Int): Single<MovieDatabaseModel>

//    @Query("SELECT COUNT(*) from favorite_movies")
//    fun countFavoriteMovies(): Int

    @Insert
    fun insert(movie: MovieDatabaseModel)

//    @Delete
//    fun delete(movie: MovieDatabaseModel)

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    fun deleteById(movieId: Int)
}