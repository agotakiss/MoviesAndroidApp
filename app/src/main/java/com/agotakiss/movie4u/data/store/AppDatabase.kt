package com.agotakiss.movie4u.data.store

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.agotakiss.movie4u.data.mapper.GenreConverter
import com.agotakiss.movie4u.data.models.MovieDatabaseModel

@Database(entities = arrayOf(MovieDatabaseModel::class), version = 1)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao
}