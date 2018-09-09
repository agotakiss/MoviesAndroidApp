package com.agotakiss.movie4u.data.store

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.agotakiss.movie4u.data.models.MovieDatabaseModel
import io.reactivex.Single

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): Single<List<MovieDatabaseModel>>

    @Query("SELECT * FROM favorite_movies where id LIKE  :movieId")
    fun findByMovieId(movieId: Int): Single<MovieDatabaseModel>

    @Insert
    fun insert(movie: MovieDatabaseModel)

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    fun deleteById(movieId: Int)
}