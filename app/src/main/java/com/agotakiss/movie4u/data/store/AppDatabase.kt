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

//    companion object {
//
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext,
//                AppDatabase::class.java, "movies_database")
//                .build()
//    }
}